package mx.com.sharkit.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import mx.com.sharkit.customdao.IBaseRepositorio;
import mx.com.sharkit.domain.DocumentoChecklist;

/**
 * Spring Data repository for the DocumentoChecklist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DocumentoChecklistRepository  extends IBaseRepositorio<DocumentoChecklist, Long>, JpaSpecificationExecutor<DocumentoChecklist> {

}

