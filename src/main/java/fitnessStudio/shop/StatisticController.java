package fitnessStudio.shop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;

import javax.money.MonetaryAmount;

import org.javamoney.moneta.Money;
import org.salespointframework.order.Order;
import org.salespointframework.order.OrderLine;
import org.salespointframework.order.OrderManagement;
import org.salespointframework.order.OrderStatus;
import org.salespointframework.quantity.Quantity;
import org.salespointframework.time.Interval;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;

import fitnessStudio.FitnessStudio;
import fitnessStudio.request.PauseRequest;
import fitnessStudio.request.Request;
import fitnessStudio.user.StudioUser;
import fitnessStudio.user.StudioUserManagement;
import fitnessStudio.user.Contract;

import org.springframework.security.access.prepost.PreAuthorize;
import static org.salespointframework.core.Currencies.*;

@Controller
public class StatisticController {
	
	private final OrderManagement<Order> orderManagement;
	private final FitnessStudio fitnessStudio;
	private final StudioUserManagement studioUserManagement;
	
	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public StatisticController(OrderManagement<Order> orderManagement, FitnessStudio fitnessStudio,
			StudioUserManagement studioUserManagement) {
		Assert.notNull(orderManagement, "OrderManagement must not be null!");
		Assert.notNull(fitnessStudio, "FitnessStudio must not be null!");
		Assert.notNull(studioUserManagement, "StudioUsermanagement must not be null!");
		this.orderManagement = orderManagement;
		this.fitnessStudio = fitnessStudio;
		this.studioUserManagement = studioUserManagement;
	}

	/*add attributes to mapping*/
	@PreAuthorize("hasAnyRole('EMPLOYEE','BOSS')")
	@GetMapping("/shopStatistics")
	public String listTransactions(Model model) {
		Assert.notNull(model, "Model must not be null!");
		model.addAttribute("OrderList", orderManagement.findBy(OrderStatus.COMPLETED));
		model.addAttribute("DailySales", getDailySales());
		model.addAttribute("MonthlySales", getMonthlySales());
		model.addAttribute("MonthlyCosts", getMonthlyCosts());
		model.addAttribute("MonthlyMembershipFees", getMonthlyMembershipFees());
		model.addAttribute("MonthlyProfit", getMonthlyProfit());
		model.addAttribute("MostWantedProducts", getMostSoldProducts());
		model.addAttribute("PieChartData", mapTransactionCountInt());
		Assert.isTrue(!model.asMap().containsValue(null), "Model must not contain null");
		return "shopStatistics";
	}
	
	/*calculate daily sales*/
	public MonetaryAmount getDailySales() {
		MonetaryAmount money = Money.of(BigDecimal.ZERO, EURO);
		List<Order> dailyOrders = orderManagement.findBy(Interval.from(LocalDateTime.now().withHour(0).withMinute(0)
				.withSecond(0)).to(LocalDateTime.now())).toList();
		for (Order order : dailyOrders) {
			money = money.add(order.getTotal());
		}
		
		return money;
	}
	
	public MonetaryAmount getMonthlySales() {
		MonetaryAmount money = Money.of(BigDecimal.ZERO, EURO);
		List<Order> monthlyOrders = orderManagement.findBy(Interval.from(LocalDateTime.now()
				.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0)).to(LocalDateTime.now())).toList();
		for (Order order : monthlyOrders) {
			money = money.add(order.getTotal());
		}
		return money;
	}
	
	public MonetaryAmount getMonthlyCosts() {
		MonetaryAmount money = Money.of(BigDecimal.ZERO, EURO);
		money = money.subtract(fitnessStudio.getMonthlyPowerCosts())
				.subtract(fitnessStudio.getMonthlyWaterCosts())
				.subtract(fitnessStudio.getMonthlyRentalCosts());
		return money;
	}
	
	public MonetaryAmount getMonthlyMembershipFees() {
		MonetaryAmount money = Money.of(0, EURO);
		for (StudioUser i : studioUserManagement.findAllUsers()) {
			boolean paused = false;
			Contract contract = i.getContract();
			for (Request p : contract.getRequests()) {
				if (p.getClass().equals(PauseRequest.class) && ((PauseRequest) p).getMonth().equals(YearMonth.now())) {
						paused = paused || true;
				}
			}
			if (!paused) {
				money = money.add(contract.getPrice());
			}
		}
		return money;
	}
	
	public MonetaryAmount getMonthlyProfit() {
		MonetaryAmount money = Money.of(BigDecimal.ZERO, EURO);
		List<Order> monthlyOrders = orderManagement.findBy(Interval.from(LocalDateTime.now().withDayOfMonth(1)
				.withHour(0).withMinute(0).withSecond(0)).to(LocalDateTime.now())).toList();
		for (Order order : monthlyOrders) {
			money = money.add(order.getTotal());
		}
		money = money.subtract(fitnessStudio.getMonthlyPowerCosts())
				.subtract(fitnessStudio.getMonthlyWaterCosts())
				.subtract(fitnessStudio.getMonthlyRentalCosts());
		int count = 0;
		for (StudioUser i : studioUserManagement.findAllUsers()) {
			count++;
		}
		money = money.add(Money.of(fitnessStudio.getMembershipFee()
				.getNumber().intValue(), EURO).multiply(count));
		return money;
	}
	
	public ArrayList<String> getMostSoldProducts() {
		HashMap<String, Integer> amounts = new HashMap<String, Integer>();
		List<Order> Orders = orderManagement.findBy(OrderStatus.COMPLETED).toList();
		for (Order order : Orders) {
			for (OrderLine orderLine : order.getOrderLines()) {
				if(amounts.containsKey(orderLine.getProductName())) {
					amounts.replace(orderLine.getProductName(), orderLine.getQuantity().getAmount().intValue()
							+ amounts.get(orderLine.getProductName()).intValue());
				}else {
					amounts.put(orderLine.getProductName(), orderLine.getQuantity().getAmount().intValue());
				}
			}
		}
		amounts.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> amounts.put(x.getKey(),
				x.getValue()));
		ArrayList<String> maxItems = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : amounts.entrySet()) {
			maxItems.add(entry.getKey());
		}
		return maxItems;
	}
	
	public TreeMap<String, Quantity> mapTransactionCount(){
		TreeMap<String, Integer> amounts = mapTransactionCountInt();
		TreeMap<String, Quantity> tmp = new TreeMap<String, Quantity>();
		for (Map.Entry<String, Integer> entry : amounts.entrySet()) {
			tmp.put(entry.getKey(), Quantity.of((double) entry.getValue()));
		}
		return tmp;
		
	}
	
	public TreeMap<String, Integer> mapTransactionCountInt(){
		TreeMap<String, Integer> amounts= new TreeMap<>();
		for (Order order : orderManagement.findBy(OrderStatus.COMPLETED)) {
			for (OrderLine orderLine : order.getOrderLines()) {
				if(amounts.containsKey(orderLine.getProductName())) {
					amounts.replace(orderLine.getProductName(), orderLine.getQuantity().getAmount().intValue()
							+ amounts.get(orderLine.getProductName()).intValue());
				}else {
					amounts.put(orderLine.getProductName(), orderLine.getQuantity().getAmount().intValue());
				}
			}
		}
		amounts.entrySet().stream().sorted(Map.Entry.comparingByValue()).forEachOrdered(x -> amounts.put(x.getKey(),
				x.getValue()));
		ArrayList<String> maxItems = new ArrayList<String>();
		for (Map.Entry<String, Integer> entry : amounts.entrySet()) {
			maxItems.add(entry.getKey());
		}
		return amounts;
		
	}
	
	

}
