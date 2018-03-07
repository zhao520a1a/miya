package com.miya.datasourceconfig;

//
///*数据源配置: Mybatis各项配置 */
//@Configuration
//@MapperScan(basePackages = "com.miya.dao", sqlSessionTemplateRef = "mysqlSqlSessionTemplate")
//public class DataSourceConfig {
//
//	@Bean(name = "mysqlDataSource")
//	@ConfigurationProperties(prefix = "mysql.spring.datasource")
//	@Primary
//	public DataSource testDataSource() {
//		return DataSourceBuilder.create().build();
//	}
//
//	@Bean(name = "mysqlSqlSessionFactory")
//	@Primary
//	public SqlSessionFactory testSqlSessionFactory(@Qualifier("mysqlDataSource") DataSource dataSource)
//			throws Exception {
//		SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
//		bean.setDataSource(dataSource);
//		bean.setMapperLocations(
//				new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/*.xml"));
//		bean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml"));
//		return bean.getObject();
//	}
//
//	@Bean(name = "mysqlTransactionManager")
//	@Primary
//	public DataSourceTransactionManager testTransactionManager(@Qualifier("mysqlDataSource") DataSource dataSource) {
//		return new DataSourceTransactionManager(dataSource);
//	}
//
//
//	@Bean(name = "mysqlSqlSessionTemplate")
//	@Primary
//	public SqlSessionTemplate testSqlSessionTemplate(
//			@Qualifier("mysqlSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
//		return new SqlSessionTemplate(sqlSessionFactory);
//	}
//
//}