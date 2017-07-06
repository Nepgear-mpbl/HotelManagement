/*
 * Copyright (c) 2017 JavaDog
 */
package cn.zhengzhaoyu.summerSemester.common.model;

import cn.zhengzhaoyu.summerSemester.common.Config_Javadog;
import com.jfinal.plugin.activerecord.dialect.MysqlDialect;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.druid.DruidPlugin;

/**
 * 自动生成数据库中的类的生成器
 *
 * @author ZZY
 * @version 1.0.0
 * @since 1.0.0
 */
public class _Generator_Javadog {

    public static void main(String[] args) {

        String baseModelPackageName = "cn.zhengzhaoyu.summerSemester.common.model.base";
        String baseModelOutputDir = "src/main/java/cn/zhengzhaoyu/summerSemester/common/model/base";
        String modelPackageName = "cn.zhengzhaoyu.summerSemester.common.model";
        String modelOutputDir = "src/main/java/cn/zhengzhaoyu/summerSemester/common/model";

        DruidPlugin dp = new Config_Javadog().getDruidPlugin();
        dp.start();

        Generator generator = new Generator(dp.getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);

        generator.setDialect(new MysqlDialect());
        generator.setGenerateChainSetter(true);
        generator.setGenerateDaoInModel(false);
        generator.setRemovedTableNamePrefixes("j_");
        generator.setGenerateDataDictionary(false);

        generator.generate();

        dp.stop();

    }

}
