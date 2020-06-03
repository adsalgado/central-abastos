package mx.com.sharkit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

import mx.com.sharkit.service.NotificacionAsyncService;
import mx.com.sharkit.service.NotificacionService;
import mx.com.sharkit.service.dto.NotificacionDTO;

@Service
public class NotificacionAsyncServiceImpl implements NotificacionAsyncService {
	
	@Autowired
	private NotificacionService notificacionService;
	
	@Autowired
    TaskExecutor taskExecutor;

	@Override
	public void save(NotificacionDTO notificacionDTO) {
		taskExecutor.execute(() -> notificacionService.save(notificacionDTO));
	}

}
