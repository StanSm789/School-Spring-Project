package com.smirnov.springschooldatabase.config;

import com.smirnov.springschooldatabase.dao.mappers.CourseMapper;
import com.smirnov.springschooldatabase.dao.mappers.GroupMapper;
import com.smirnov.springschooldatabase.dao.mappers.LessonMapper;
import com.smirnov.springschooldatabase.dao.mappers.RoomMapper;
import com.smirnov.springschooldatabase.dao.mappers.StudentMapper;
import com.smirnov.springschooldatabase.dao.mappers.TeacherMapper;
import com.smirnov.springschooldatabase.dao.mappers.TimetableMapper;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;
import javax.sql.DataSource;

@Configuration
@ComponentScan("com.smirnov.springschooldatabase")
@PropertySource("classpath:database.properties")
public class SpringJdbcConfig {

    @Value("${db.driverClassName}")
    private String driverClassName;
    @Value("${db.url}")
    private String url;
    @Value("${db.user}")
    private String username;
    @Value("${db.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.addDataSourceProperty( "cachePrepStmts" , "true" );
        dataSource.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        dataSource.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );

        return dataSource;
    }

    @Bean
    PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    TransactionTemplate transactionTemplate() {
        return new TransactionTemplate(transactionManager());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public CourseMapper courseMapper() {
        return new CourseMapper();
    }

    @Bean
    public TeacherMapper teacherMapper() {
        return new TeacherMapper();
    }

    @Bean
    public GroupMapper groupMapper() {
        return new GroupMapper();
    }

    @Bean
    public StudentMapper studentMapper() {
        return new StudentMapper();
    }

    @Bean
    public RoomMapper roomMapper() {
        return new RoomMapper();
    }

    @Bean
    LessonMapper lessonMapper() {
        return new LessonMapper(jdbcTemplate());
    }

    @Bean
    TimetableMapper timetableMapper() {
        return new TimetableMapper();
    }

}
