package ee.bcs.valiit.repository2;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HibernateRepository extends JpaRepository<Bankclient, Long> {
    Bankclient findByFirstname(String firstname);
    List<Bankclient> findAllByFirstnameIgnoreCase(String firstname);
    List<Bankclient> findAllById(Long id);

}
