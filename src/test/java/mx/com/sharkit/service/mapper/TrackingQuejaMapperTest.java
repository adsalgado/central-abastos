package mx.com.sharkit.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TrackingQuejaMapperTest {

    private TrackingQuejaMapper trackingQuejaMapper;

    @BeforeEach
    public void setUp() {
        //trackingQuejaMapper = new TrackingQuejaMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(trackingQuejaMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(trackingQuejaMapper.fromId(null)).isNull();
    }
}
