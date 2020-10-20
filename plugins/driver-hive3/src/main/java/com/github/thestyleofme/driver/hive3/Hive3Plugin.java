package com.github.thestyleofme.driver.hive3;

import com.github.thestyleofme.plugin.framework.realize.BasePlugin;
import lombok.extern.slf4j.Slf4j;
import org.pf4j.PluginWrapper;

/**
 * @author zhilong.deng
 */
@Slf4j
public class Hive3Plugin extends BasePlugin {

    public Hive3Plugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    protected void startEvent() {
        log.info("hive3 plugin start...");
    }

    @Override
    protected void deleteEvent() {
        log.info("hive3 plugin delete...");
    }

    @Override
    protected void stopEvent() {
        log.info("hive3 plugin stop...");
    }
}
