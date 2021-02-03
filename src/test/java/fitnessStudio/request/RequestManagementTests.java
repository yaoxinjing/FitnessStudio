package fitnessStudio.request;




import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class RequestManagementTests {
	
	@SuppressWarnings("unused")
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	RequestManagement requestManagement;
	
	@Autowired
	RequestRepository requests;
	
	@BeforeEach
    public void setUp() {
    
	}
	
	/*@Test
	void testAcceptRequest() {
		long id = requests.findAll().iterator().next().getId();
		requestManagement.acceptRequest(id, null);
		String result = requestManagement.findById(id).getState();
		assertEquals(result, "AKZEPTIERT");
	}*/
	
//	@Test
//	void testFindByStateAndType() {
//		Request request = new VacationRequest(LocalDate.parse("2021-02-22"), LocalDate.parse("2021-02-27"), "keine Angabe");
//		entityManager.persist(request);
//		entityManager.flush();
//		
//		LinkedList<Request> result = new LinkedList<Request>();
//		result.add(new VacationRequest(LocalDate.parse("2021-02-22"), LocalDate.parse("2021-02-27"), "keine Angabe"));
//		assertEquals(result, requestManagement.findByStateAndType(RequestStateType.EINGEREICHT, RequestType.URLAUBSANTRAG));
//	}

}
