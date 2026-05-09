/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.robotmonitor.common.utils.StringUtils
 *  org.apache.ibatis.io.VFS
 *  org.apache.ibatis.session.SqlSessionFactory
 *  org.mybatis.spring.SqlSessionFactoryBean
 *  org.mybatis.spring.boot.autoconfigure.SpringBootVFS
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 *  org.springframework.core.env.Environment
 *  org.springframework.core.io.DefaultResourceLoader
 *  org.springframework.core.io.Resource
 *  org.springframework.core.io.ResourceLoader
 *  org.springframework.core.io.support.PathMatchingResourcePatternResolver
 *  org.springframework.core.type.classreading.CachingMetadataReaderFactory
 *  org.springframework.core.type.classreading.MetadataReader
 *  org.springframework.util.ClassUtils
 */
package com.robotmonitor.framework.config;

import com.robotmonitor.common.utils.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import javax.sql.DataSource;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;

@Configuration
public class MyBatisConfig {
    @Autowired
    private Environment env;
    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    public static String setTypeAliasesPackage(String typeAliasesPackage) {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory((ResourceLoader)resolver);
        ArrayList allResult = new ArrayList();
        try {
            for (String string : typeAliasesPackage.split(",")) {
                ArrayList<String> result = new ArrayList<String>();
                String string2 = "classpath*:" + ClassUtils.convertClassNameToResourcePath((String)string.trim()) + "/**/*.class";
                Resource[] resources = resolver.getResources(string2);
                if (resources != null && resources.length > 0) {
                    MetadataReader metadataReader = null;
                    for (Resource resource : resources) {
                        if (!resource.isReadable()) continue;
                        metadataReader = metadataReaderFactory.getMetadataReader(resource);
                        try {
                            result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
                        }
                        catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (result.size() <= 0) continue;
                HashSet hashResult = new HashSet(result);
                allResult.addAll(hashResult);
            }
            if (allResult.size() <= 0) {
                throw new RuntimeException("mybatis typeAliasesPackage \u8def\u5f84\u626b\u63cf\u9519\u8bef,\u53c2\u6570typeAliasesPackage:" + typeAliasesPackage + "\u672a\u627e\u5230\u4efb\u4f55\u5305");
            }
            typeAliasesPackage = String.join((CharSequence)",", allResult.toArray(new String[0]));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return typeAliasesPackage;
    }

    public Resource[] resolveMapperLocations(String[] mapperLocations) {
        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        ArrayList<Resource> resources = new ArrayList<Resource>();
        if (mapperLocations != null) {
            for (String mapperLocation : mapperLocations) {
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resources.addAll(Arrays.asList(mappers));
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
        }
        return resources.toArray(new Resource[resources.size()]);
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        String typeAliasesPackage = this.env.getProperty("mybatis.typeAliasesPackage");
        String mapperLocations = this.env.getProperty("mybatis.mapperLocations");
        String configLocation = this.env.getProperty("mybatis.configLocation");
        typeAliasesPackage = MyBatisConfig.setTypeAliasesPackage(typeAliasesPackage);
        VFS.addImplClass(SpringBootVFS.class);
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setTypeAliasesPackage(typeAliasesPackage);
        sessionFactory.setMapperLocations(this.resolveMapperLocations(StringUtils.split((String)mapperLocations, (String)",")));
        sessionFactory.setConfigLocation(new DefaultResourceLoader().getResource(configLocation));
        return sessionFactory.getObject();
    }
}
