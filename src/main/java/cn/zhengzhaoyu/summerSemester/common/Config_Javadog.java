/*
 * Copyright (c) 2017 JavaDog
 */
package cn.zhengzhaoyu.summerSemester.common;

import cn.zhengzhaoyu.summerSemester.common.model._MappingKit;
import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.redis.RedisPlugin;
import com.jfinal.template.Engine;

/**
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class Config_Javadog extends JFinalConfig {

    private static final Prop p = loadConfig();

    //加载配置
    private static Prop loadConfig() {
        try {
            return PropKit.use("config-dev.properties");
        } catch (RuntimeException e) {
            LogKit.logNothing(e);
            return PropKit.use("config.properties");
        }
    }

    public static void main(String[] args) {
        JFinal.start("src/main/webapp", 80, "/");
    }

    /**
     * @see com.jfinal.config.JFinalConfig#configConstant(Constants)
     */
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(p.getBoolean("devMode"));
        me.setJsonFactory(MixedJsonFactory.me());
    }

    /**
     * @see com.jfinal.config.JFinalConfig#configRoute(Routes)
     */
    @Override
    public void configRoute(Routes me) {
        me.add(new FrontRoute_Javadog());
    }

    /**
     * @see com.jfinal.config.JFinalConfig#configEngine(Engine)
     */
    @Override
    public void configEngine(Engine me) {
        me.addSharedFunction("/_view/common/__layout.html");
    }

    /**
     * @see com.jfinal.config.JFinalConfig#configPlugin(Plugins)
     */
    @Override
    public void configPlugin(Plugins me) {
        DruidPlugin dp = getDruidPlugin();
        me.add(dp);
        ActiveRecordPlugin arp = getActiveRecordPlugin(dp);
        me.add(arp);
        me.add(getRedisPlugin());
    }

    /**
     * @see com.jfinal.config.JFinalConfig#configInterceptor(Interceptors)
     */
    @Override
    public void configInterceptor(Interceptors me) {

    }

    /**
     * @see com.jfinal.config.JFinalConfig#configHandler(Handlers)
     */
    @Override
    public void configHandler(Handlers me) {

    }

    /**
     * 获取druid插件的配置
     * @return DruidPlugin
     */
    public DruidPlugin getDruidPlugin() {
        String url = p.get("druid.url");
        String username = p.get("druid.username");
        String password = p.get("druid.password");
        String driverClass = p.get("druid.driverClass");
        return new DruidPlugin(url, username, password, driverClass);
    }

    private RedisPlugin getRedisPlugin() {
        String host = p.get("redis.host");
        int port = p.getInt("redis.port");
        int timeout = ElKit.eval(p.get("redis.timeout"));
        String password = p.get("redis.password");
        int database = p.getInt("redis.tokenDatabase");
        return new RedisPlugin("token", host, port, timeout, password, database);
    }

    private ActiveRecordPlugin getActiveRecordPlugin(IDataSourceProvider provider) {
        ActiveRecordPlugin arp = new ActiveRecordPlugin(provider);
        arp.setDialect(new MysqlDialect());
        _MappingKit.mapping(arp);
        arp.setBaseSqlTemplatePath(PathKit.getWebRootPath() + "/_sql");
        arp.addSqlTemplate("all.sql");
        return arp;
    }
}
