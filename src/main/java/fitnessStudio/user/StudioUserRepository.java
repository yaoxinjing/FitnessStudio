package fitnessStudio.user;

import java.util.List;
import java.util.Optional;

import org.salespointframework.useraccount.UserAccount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.util.Streamable;


public interface StudioUserRepository extends CrudRepository<StudioUser, Long> {

	/**
	 * Re-declared {@link CrudRepository#findAll()} to return a {@link Streamable} instead of {@link Iterable}.
	 */
	@Override
	Streamable<StudioUser> findAll();
	
	StudioUser findByUserAccount(UserAccount studioUserAccount);
	
    Optional<StudioUser> findById(Long id);
    List<StudioUser> findByIsPresent(boolean present);


}
