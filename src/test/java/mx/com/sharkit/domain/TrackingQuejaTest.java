package mx.com.sharkit.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import mx.com.sharkit.web.rest.TestUtil;

public class TrackingQuejaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TrackingQueja.class);
        TrackingQueja trackingQueja1 = new TrackingQueja();
        trackingQueja1.setId(1L);
        TrackingQueja trackingQueja2 = new TrackingQueja();
        trackingQueja2.setId(trackingQueja1.getId());
        assertThat(trackingQueja1).isEqualTo(trackingQueja2);
        trackingQueja2.setId(2L);
        assertThat(trackingQueja1).isNotEqualTo(trackingQueja2);
        trackingQueja1.setId(null);
        assertThat(trackingQueja1).isNotEqualTo(trackingQueja2);
    }
}
