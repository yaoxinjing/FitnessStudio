package fitnessStudio;


import org.salespointframework.core.DataInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
@Order(1)	//Priority
public class FitnessStudioDataInitializer implements DataInitializer {
	private static final Logger LOG = LoggerFactory.getLogger(FitnessStudioDataInitializer.class);
	private  OperationTimeRepositiory operationTimeRepositiory;

	/* ===Constructor=== ʕ•́ᴥ•̀ʔ */
	public FitnessStudioDataInitializer(OperationTimeRepositiory operationTimeRepositiory) {
		Assert.notNull(operationTimeRepositiory, "operationTimeRepositiory must not be null!");
		this.operationTimeRepositiory = operationTimeRepositiory;
	}

	//set Default Values
	@Override
	public void initialize() {
		/*
		 * 	Every Restart we default Operation Time?
		 */
		LOG.info("Creating default operation time for 7 days");
		OperationTime operationTime = new OperationTime();
		operationTime.setName("default");

		operationTime.setMonday(new Workday("05:00","18:00"));
		operationTime.setThursday(new Workday("06:00","18:00"));
		operationTime.setTuesday(new Workday("06:00","18:00"));
		operationTime.setWednesday(new Workday("06:00","18:00"));
		operationTime.setFriday(new Workday("06:00","18:00"));
		operationTime.setSaturday(new Workday("06:00","18:00"));
		operationTime.setSunday(new Workday("06:00","18:00"));
		operationTimeRepositiory.save(operationTime);
	}


}
