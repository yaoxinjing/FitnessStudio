package fitnessStudio.request;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Repository;

/** 
 * Repository for saving requests, also provides basic search functionality
 */
@Repository
public interface RequestRepository extends CrudRepository<Request, Long>{
	
	@Override
	Streamable<Request> findAll();
}
