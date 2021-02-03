package fitnessStudio;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface OperationTimeRepositiory extends CrudRepository<OperationTime, Long> {

	//Find entrys by Name
	OperationTime findByName(String name);
	//Find Entrys by Id
	Optional<OperationTime> findById(Long id);
}
