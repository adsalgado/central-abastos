package mx.com.sharkit.service.impl;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import mx.com.sharkit.domain.Producto;
import mx.com.sharkit.service.UtilService;

@Service
@Transactional
public class UtilServiceImpl extends BaseServiceImpl<Producto, Long> implements UtilService {

}
