package net.jeeshop.core.task;

import java.util.concurrent.TimeUnit;

import net.jeeshop.core.front.SystemManager;
import net.jeeshop.core.oscache.FrontCache;
import org.slf4j.LoggerFactory;

/**
 * 前台缓存定时更新
 * 
 * @author huangf
 * 
 */
public class FrontCacheTask implements Runnable {
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ManageCacheTask.class);
	private FrontCache frontCache;

	public void setFrontCache(FrontCache frontCache) {
		this.frontCache = frontCache;
	}


	@Override
	public void run() {
		while (true) {
			
			try {
//				TimeUnit.MINUTES.sleep(15);//单位：分钟
				TimeUnit.SECONDS.sleep(Long.valueOf(SystemManager.getInstance().get("task_FrontCacheTask_time")));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			logger.error("FrontCacheTask.run...");
			try {
				frontCache.loadAllCache();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

}
