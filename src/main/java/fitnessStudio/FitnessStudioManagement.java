package fitnessStudio;




import fitnessStudio.catalog.Commodity;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserRepository;
import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.money.MonetaryAmount;
import java.time.LocalDateTime;
import java.util.*;

import static org.salespointframework.core.Currencies.EURO;

@Service
public class FitnessStudioManagement {
	private static final Logger LOG = LoggerFactory.getLogger(FitnessStudioManagement.class);
	
	private final OperationTimeRepositiory operationTimeRepositiory;
	private final OrderManagement<Order> orderManagement;
	private final StudioUserRepository studioUserRepository;
	private final FitnessStudio fitnessStudio;



	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public FitnessStudioManagement(StudioUserRepository studioUserRepository,
					OrderManagement<Order> orderManagement, OperationTimeRepositiory operationTimeRepositiory,
					FitnessStudio fitnessStudio) {
		
		this.operationTimeRepositiory = operationTimeRepositiory;
		this.orderManagement = orderManagement;
		this.studioUserRepository=studioUserRepository;
		this.fitnessStudio = fitnessStudio;
	}

	/* ===Methods=== ʕ•́ᴥ•̀ʔ */
	//set OperationTime
	public void changeOprationTime(OperationForm form) {
		Assert.notNull(form, "OperationForm must not be null!");

		OperationTime defaultOp = operationTimeRepositiory.findByName("default");

		defaultOp.setMonday(form.getMonday());
		defaultOp.setSunday(form.getSunday());
		defaultOp.setSaturday(form.getSaturday());
		defaultOp.setFriday(form.getFriday());
		defaultOp.setWednesday(form.getWednesday());
		defaultOp.setTuesday(form.getTuesday());
		defaultOp.setThursday(form.getThursday());

		operationTimeRepositiory.save(defaultOp);
	}

	//change login user balance
	public void changeBalance(StudioUser currentUser, double amount){
		currentUser.addBalance(Money.of(amount, EURO));
		studioUserRepository.save(currentUser);
		LOG.info("Changed Money of "+currentUser.getUsername()+
				" by "+amount+"€ to "+Commodity.MoneySymbol(currentUser.getBalance().toString()));
		
	}

	//total training time in current mouth
	public int getTotalVisitingTime(StudioUser currentUser){
		if(currentUser.getLoginLogoutTimestamp() == null){
			return 0;
		}
		ArrayList<Pair<Date,Date>> loginLogoutTimeStamp = currentUser.getLoginLogoutTimestamp();
		double totalTime = 0;
		for(Pair<Date,Date> item:loginLogoutTimeStamp){
			if(item.getSecond() != null && isInCurrentMouth(item.getFirst())){
					totalTime+= getTimePeriod(item);  // in minutem
			}
		}

		return (int)totalTime;
	}

	public double getTimePeriod(Pair<Date,Date> pair){
		Date loginTime = pair.getFirst();
		Date logouTime = pair.getSecond();

		double difference = (double)(logouTime.getTime() - loginTime.getTime())/60000; //// now you have your answer in min

		return difference;

	}

	//get visiting time in current mouth
	public int getVisitingCount(StudioUser currentUser){
		if(currentUser.getLoginLogoutTimestamp() == null){
			return 0;
		}
		ArrayList<Pair<Date,Date>> loginLogoutTimeStamp = currentUser.getLoginLogoutTimestamp();
		int count=0;
		for(Pair<Date,Date> item:loginLogoutTimeStamp){
			if(item.getSecond() != null && isInCurrentMouth(item.getFirst())){
					count++;
			}
		}
		return count;
	}


	// Amount of money the current user has spend in this mouth
	public MonetaryAmount moneySpendThisMonth(StudioUser currentUser){
		MonetaryAmount spendMoney = Money.of(0, EURO);
		for (Order order : currentMonthOrders(currentUser)) {
			spendMoney.add(order.getTotal());
		}
		return spendMoney;
	}


	//get all the transaction in this mouth
	public List<Order> currentMonthOrders(StudioUser currentUser){
		Interval currentMonth = Interval.from(LocalDateTime.of(LocalDateTime.now().getYear(),
				LocalDateTime.now().getMonth(), 1, 0, 0)).to(LocalDateTime.of(LocalDateTime.now().getYear(),
						LocalDateTime.now().getMonth(), LocalDateTime.now().toLocalDate().lengthOfMonth(), 23, 59));
		return orderManagement.findBy(currentUser.getUserAccount(),currentMonth).toList();
	}

	//check whether the transaction is in this month
	public boolean isInCurrentMouth(Date date){
		if(date == null) {
			return false;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int currentYear = calendar.get(Calendar.YEAR);
		int currentMonth = calendar.get(Calendar.MONTH);
		//	int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

		Calendar calendarFortest = Calendar.getInstance();
		calendar.setTimeInMillis(date.getTime());
		int mYear = calendarFortest.get(Calendar.YEAR);
		int mMonth = calendarFortest.get(Calendar.MONTH);
		//	int mDay = calendarFortest.get(Calendar.DAY_OF_MONTH);

		if(mYear<currentYear){
			return false;
		} else if(mMonth<currentMonth){
			return false;
		} else{
			return true;
		}
	}



	public boolean addAffiliateBonus(String userName,StudioUser loginUser){
		StudioUser targetUser = null;
		if(!userName.equals(loginUser.getUsername())) {
			for(StudioUser user : studioUserRepository.findAll()) {
				if (user.getUsername().equals(userName)) {
					targetUser = user;
					break;
				}
			}
		}
		if(targetUser == null){
			loginUser.setUserNameFalsh(true); // input user name is wrong
			studioUserRepository.save(loginUser);
			return false;
		}
		loginUser.setSetAffiliateBonus(true); // successfully add bonus to a member who invite login user
		targetUser.addBalance(fitnessStudio.getAffiliateBonus());
		studioUserRepository.save(loginUser);
		studioUserRepository.save(targetUser);
		LOG.info("Added Affiliate Bonus to "+targetUser.getUsername()+" of "+10+"€");
		return true;
	}
}