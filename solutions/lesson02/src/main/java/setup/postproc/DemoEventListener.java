package setup.postproc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class DemoEventListener implements ApplicationListener<ApplicationEvent> {
	private static final Logger log = LoggerFactory.getLogger(DemoEventListener.class);
	
	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		log.info("application event: " + event);
	}

}
