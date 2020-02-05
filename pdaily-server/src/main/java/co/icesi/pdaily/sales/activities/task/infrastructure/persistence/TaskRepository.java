package co.icesi.pdaily.sales.activities.task.infrastructure.persistence;

import static co.haruk.core.domain.model.guards.Guards.requireNonNull;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

import co.haruk.core.domain.model.entity.PlainName;
import co.haruk.core.domain.model.persistence.QueryParameter;
import co.haruk.core.infrastructure.persistence.jpa.JPARepository;
import co.icesi.pdaily.sales.activities.task.domain.model.Task;

@ApplicationScoped
public class TaskRepository extends JPARepository<Task> {
	public Optional<Task> findByName(PlainName name) {
		requireNonNull( name );
		return findSingleWithNamedQuery(
				Task.findByName,
				QueryParameter.with( "name", name.text() ).parameters()
		);
	}
}
