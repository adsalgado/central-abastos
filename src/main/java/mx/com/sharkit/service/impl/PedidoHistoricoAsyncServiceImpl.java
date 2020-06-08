package mx.com.sharkit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import mx.com.sharkit.service.PedidoHistoricoAsyncService;
import mx.com.sharkit.service.PedidoProveedorHistoricoService;
import mx.com.sharkit.service.dto.PedidoProveedorHistoricoDTO;

@Service
public class PedidoHistoricoAsyncServiceImpl implements PedidoHistoricoAsyncService {

	@Autowired
	private PedidoProveedorHistoricoService service;
	
	@Autowired
    TaskExecutor taskExecutor;

	@Override
	public void savePedidoProveedorHistorico(PedidoProveedorHistoricoDTO historicoDTO) {
		taskExecutor.execute(() -> service.save(historicoDTO));
	}

}
