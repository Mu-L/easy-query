package com.easy.query.test;

import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.test.dto.ValueCompanyDTO;
import com.easy.query.test.entity.Company;
import com.easy.query.test.entity.CompanyAddress;
import com.easy.query.test.entity.CompanyCity;
import com.easy.query.test.entity.company.ValueCompany;
import com.easy.query.test.entity.company.ValueCompanyAddress;
import com.easy.query.test.entity.company.ValueCompanyLicense;
import com.easy.query.test.entity.company.ValueCompanyLicenseExtra;
import com.easy.query.test.entity.proxy.CompanyProxy;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Supplier;

/**
 * create time 2023/11/6 08:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class ValueObjectTest extends BaseTest {

    @Test
    public void voTest1() {
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .asTable("company_a")
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id`,`name`,`province`,`city`,`area` FROM `company_a`", sql);
    }

    @Test
    public void voTest2() {
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .asTable("company_a")
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id`,`name`,`province`,`city`,`area` FROM `company_a`", sql);
    }

    @Test
    public void voTest3() {
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o -> {
                            o.column("address.province");
                        })
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `province` FROM `company_a`", sql);
    }

    @Test
    public void voTest4() {
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o -> {
                            o.column("id");
                            o.column("address.province").columnIgnore("address");
                        })
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id` FROM `company_a`", sql);
    }

    @Test
    public void voTest5() {
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o -> {
                            o.columnAll().columnIgnore("address");
                        })
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `id`,`name` FROM `company_a`", sql);
    }

    @Test
    public void voTest6() {
        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyQueryClient.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o -> {
                            o.column("address");
                        })
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT `province`,`city`,`area` FROM `company_a`", sql);
    }

    //    @Test
//    public void voTest7(){
//        Supplier<Exception> f = () -> {
//            try {
//                List<Company> list = easyEntityQuery.queryable(Company.class)
//                        .asTable("company_a")
//                        //.select(o->o.column(Company::getAddress))
//                        .select(o->{
//                            o.column();
//                        })
//                        .toList();
//            }catch (Exception ex){
//                return ex;
//            }
//            return null;
//        };
//        Exception exception = f.get();
//        Assert.assertNotNull(exception);
//        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
//        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
//        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
//        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
//        String sql = easyQuerySQLStatementException.getSQL();
//        Assert.assertEquals("SELECT `province`,`city`,`area` FROM `company_a`",sql);
//    }
    @Test
    public void voTest7() {

        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .asTable("company_a")
                        //.select(o->o.column(Company::getAddress))
                        .select(o -> {
                            CompanyProxy r = new CompanyProxy();
                            r.selectExpression(o.address().province());
                            return r;
                        })
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT t.`province` FROM `company_a` t", sql);
    }

    @Test
    public void voTest9() {

        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .asTable("company_a")
                        .select(o -> {
                            return o.FETCHER.address().fetchProxy();
                        })
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT t.`province`,t.`city`,t.`area` FROM `company_a` t", sql);
    }

    @Test
    public void voTest10() {

        Supplier<Exception> f = () -> {
            try {
                List<Company> list = easyEntityQuery.queryable(Company.class)
                        .asTable("company_a")
                        .where(o -> o.address().area().eq("1"))
                        .where(o -> o.address().province().eq("2"))
                        .select(o -> {
                            return o.FETCHER.address();
                        })
                        .toList();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("SELECT t.`province`,t.`city`,t.`area` FROM `company_a` t WHERE t.`area` = ? AND t.`province` = ?", sql);
    }

    @Test
    public void voTest11() {

        Supplier<Exception> f = () -> {
            try {
                Company company = new Company();
                company.setId("id");
                company.setName("name");
                CompanyAddress companyAddress = new CompanyAddress();
                companyAddress.setProvince("province");
                CompanyCity companyCity = new CompanyCity();
                companyCity.setCity("city");
                companyAddress.setCity(companyCity);
                companyAddress.setArea("area");
                company.setAddress(companyAddress);
                long l = easyEntityQuery.insertable(company).asTable("company_a").executeRows();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("INSERT INTO `company_a` (`id`,`name`,`province`,`city`,`area`) VALUES (?,?,?,?,?)", sql);
    }

    @Test
    public void voTest13() {

        Supplier<Exception> f = () -> {
            try {
                Company company = new Company();
                company.setId("id");
                company.setName("name");
                CompanyAddress companyAddress = new CompanyAddress();
                companyAddress.setProvince("province");
                companyAddress.setArea("area");
                company.setAddress(companyAddress);
                long l = easyEntityQuery.insertable(company).asTable("company_a").executeRows();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("INSERT INTO `company_a` (`id`,`name`,`province`,`area`) VALUES (?,?,?,?)", sql);
    }

    @Test
    public void voTest12() {

        Supplier<Exception> f = () -> {
            try {
                Company company = new Company();
                company.setId("id");
                company.setName("name");
                long l = easyEntityQuery.insertable(company).asTable("company_a").executeRows();
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };
        Exception exception = f.get();
        Assert.assertNotNull(exception);
        Assert.assertTrue(exception instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException easyQuerySQLCommandException = (EasyQuerySQLCommandException) exception;
        Assert.assertTrue(easyQuerySQLCommandException.getCause() instanceof EasyQuerySQLStatementException);
        EasyQuerySQLStatementException easyQuerySQLStatementException = (EasyQuerySQLStatementException) easyQuerySQLCommandException.getCause();
        String sql = easyQuerySQLStatementException.getSQL();
        Assert.assertEquals("INSERT INTO `company_a` (`id`,`name`) VALUES (?,?)", sql);
    }

//    public static  <T,T1,T2> Property<T,?> columTest1(Property<T,T1> column,Property<T1,T2> column1){
//        StringBuilder sb = new StringBuilder();
//        String propertyName = EasyLambdaUtil.getPropertyName(column);
//        sb.append(propertyName);
//        sb.append(".");
//        String propertyName1 = EasyLambdaUtil.getPropertyName(column1);
//        sb.append(propertyName1);
//        return new MultiPropertyImpl<>(sb.toString());
//    }
//
//    private String classFilePath(String className) {
//        return className.replace('.', '/') + ".class";
//    }
//
//    public static void extractLambdaMethod(Property<Company, String> lambda) {
//        try {
//            Class<?> lambdaClass = lambda.getClass();
//            Method declaredMethod = lambdaClass.getDeclaredMethod("writeReplace");
//            declaredMethod.setAccessible(Boolean.TRUE);
//            SerializedLambda serializedLambda = (SerializedLambda) declaredMethod.invoke(lambda);
//
//            String className = serializedLambda.getImplClass();
//            ClassReader classReader = new ClassReader(className);
//            classReader.accept(new ClassVisitor(Opcodes.ASM4) {
//                @Override
//                public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
//                    System.out.println(name);
//                    System.out.println(descriptor);
//                    System.out.println(signature);
//                    if (name.equals("apply") && descriptor.equals("(Ljava/lang/Object;)Ljava/lang/Object;")) {
//                        return new MethodVisitor(Opcodes.ASM4) {
//                            @Override
//                            public void visitLdcInsn(Object value) {
//                                if (value instanceof String) {
//                                    System.out.println("Lambda Expression Output: " + value);
//                                }
//                                super.visitLdcInsn(value);
//                            }
//                        };
//                    }
//                    return super.visitMethod(access, name, descriptor, signature, exceptions);
//                }
//            }, 0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void voTest15() {

        Company company = new Company();
        company.setId("id");
        company.setName("name");
        CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setProvince("province");

        CompanyCity companyCity = new CompanyCity();
        companyCity.setCity("city");
        companyAddress.setCity(companyCity);
        companyAddress.setArea("area");
        company.setAddress(companyAddress);
        Supplier<Exception> queryF = () -> {
            try {
                long l = easyEntityQuery.updatable(company).asTable("company_a").executeRows();
                System.out.println(l);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };

        Exception ex = queryF.get();
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
        Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
        String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
        Assert.assertEquals("UPDATE `company_a` SET `name` = ?,`province` = ?,`city` = ?,`area` = ? WHERE `id` = ?", sql);
    }

    @Test
    public void voTest16() {

        Company company = new Company();
        company.setId("id");
        company.setName("name");
        CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setProvince("province");
        companyAddress.setArea("area");
        company.setAddress(companyAddress);
        Supplier<Exception> queryF = () -> {
            try {
                long l = easyEntityQuery.updatable(company).asTable("company_a").setSQLStrategy(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS).executeRows();
                System.out.println(l);
            } catch (Exception ex) {
                return ex;
            }
            return null;
        };

        Exception ex = queryF.get();
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
        Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
        String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
        Assert.assertEquals("UPDATE `company_a` SET `name` = ?,`province` = ?,`area` = ? WHERE `id` = ?", sql);
    }

    @Test
    public void voTest17() {

        Company company = new Company();
        company.setId("id");
        company.setName("name");
        CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setProvince("province");
        companyAddress.setArea("area");
        company.setAddress(companyAddress);
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        Supplier<Exception> queryF = () -> {
            try {
                trackManager.begin();
                easyEntityQuery.addTracking(company);
                CompanyCity companyCity = new CompanyCity();
                companyCity.setCity("city1");
                company.getAddress().setCity(companyCity);

                long l = easyEntityQuery.updatable(company).asTable("company_a").executeRows();
                System.out.println(l);
            } catch (Exception ex) {
                return ex;
            } finally {
                trackManager.release();
            }
            return null;
        };

        Exception ex = queryF.get();
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
        Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
        String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
        Assert.assertEquals("UPDATE `company_a` SET `city` = ? WHERE `id` = ?", sql);
    }

    @Test
    public void voTest18() {

        Company company = new Company();
        company.setId("id");
        company.setName("name");
        CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setProvince("province");
        companyAddress.setArea("area");
        company.setAddress(companyAddress);
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        Supplier<Exception> queryF = () -> {
            try {
                trackManager.begin();
                easyEntityQuery.addTracking(company);

                CompanyCity companyCity = new CompanyCity();
                companyCity.setCity("city1");
                company.getAddress().setCity(companyCity);

                long l = easyEntityQuery.updatable(company).asTable("company_a").whereColumns(o -> o.FETCHER.columnKeys().address()).executeRows();
                System.out.println(l);
            } catch (Exception ex) {
                return ex;
            } finally {
                trackManager.release();
            }
            return null;
        };

        Exception ex = queryF.get();
        Assert.assertNotNull(ex);
        Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
        EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
        Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
        String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
        Assert.assertEquals("UPDATE `company_a` SET `city` = ? WHERE `id` = ? AND `province` = ? AND `city` IS NULL AND `area` = ?", sql);
    }

    @Test
    public void voTest19() {

        easyEntityQuery.deletable(Company.class)
                .whereById("123").executeRows();
        Company company = new Company();
        company.setId("123");
        company.setName("name");
        CompanyAddress companyAddress = new CompanyAddress();
        companyAddress.setProvince("province");
        companyAddress.setArea("area");
        company.setAddress(companyAddress);
        long l = easyEntityQuery.insertable(company).executeRows();
        Assert.assertEquals(1, l);
        Company company1 = easyEntityQuery.queryable(Company.class)
                .firstOrNull();
        Assert.assertNotNull(company1);
        Assert.assertNotNull(company1.getAddress().getCity());
        Assert.assertNull(company1.getAddress().getCity().getCity());
        CompanyCity companyCity1 = new CompanyCity();
        companyCity1.setCity("city123");
        company1.getAddress().setCity(companyCity1);
        long l1 = easyEntityQuery.updatable(company1).executeRows();
        Assert.assertEquals(1, l1);

        Company company2 = easyEntityQuery.queryable(Company.class)
                .firstOrNull();
        Assert.assertNotNull(company2);
        Assert.assertNotNull(company2.getAddress().getCity());
        Assert.assertNotNull(company2.getAddress().getCity().getCity());
        Assert.assertEquals("city123", company2.getAddress().getCity().getCity());
        long l2 = easyEntityQuery.updatable(Company.class)
                .setColumns(o -> o.address().city().city().set("city456"))
                .whereById("123")
                .executeRows();
        Assert.assertEquals(1, l2);


        Company company3 = easyEntityQuery.queryable(Company.class)
                .firstOrNull();
        Assert.assertNotNull(company3);
        Assert.assertNotNull(company3.getAddress().getCity());
        Assert.assertNotNull(company3.getAddress().getCity().getCity());
        Assert.assertEquals("city456", company3.getAddress().getCity().getCity());
    }


    @Test
    public void myCompany1() {
        easyQueryClient.deletable(ValueCompany.class)
                .whereById("my1").executeRows();
        ValueCompany company = new ValueCompany();
        company.setId("my1");
        company.setName("myCompany1");
        ValueCompanyAddress valueCompanyAddress = new ValueCompanyAddress();
        valueCompanyAddress.setProvince("province1");
        valueCompanyAddress.setCity("city1");
        valueCompanyAddress.setArea("area1");
        company.setAddress(valueCompanyAddress);
        ValueCompanyLicense valueCompanyLicense = new ValueCompanyLicense();
        valueCompanyLicense.setLicenseNo("license1");
        valueCompanyLicense.setLicenseDeadline(LocalDateTime.of(2023, 1, 1, 0, 0));
        ValueCompanyLicenseExtra valueCompanyLicenseExtra = new ValueCompanyLicenseExtra();
        valueCompanyLicenseExtra.setLicenseImage("www.baidu.com");
        valueCompanyLicenseExtra.setLicenseContent("it编程");
        valueCompanyLicense.setExtra(valueCompanyLicenseExtra);
        company.setLicense(valueCompanyLicense);
        long l = easyQueryClient.insertable(company).executeRows();
        Assert.assertEquals(1, l);
        List<ValueCompany> province1 = easyQueryClient.queryable(ValueCompany.class)
                .where(o -> o.eq("address.province", "province1"))
                .toList();
        Assert.assertEquals(province1.toString(), "[ValueCompany(id=my1, name=myCompany1, address=ValueCompanyAddress(province=province1, city=city1, area=area1), license=ValueCompanyLicense(licenseNo=license1, licenseDeadline=2023-01-01T00:00, extra=ValueCompanyLicenseExtra(licenseImage=www.baidu.com, licenseContent=it编程)))]");

        List<ValueCompany> province11 = easyEntityQuery.queryable(ValueCompany.class)
                .where(o -> o.address().province().eq("province1"))
                .toList();
        Assert.assertEquals(province11.toString(), "[ValueCompany(id=my1, name=myCompany1, address=ValueCompanyAddress(province=province1, city=city1, area=area1), license=ValueCompanyLicense(licenseNo=license1, licenseDeadline=2023-01-01T00:00, extra=ValueCompanyLicenseExtra(licenseImage=www.baidu.com, licenseContent=it编程)))]");


        List<ValueCompany> province2 = easyQueryClient.queryable(ValueCompany.class)
                .where(o -> o.eq("address.province", "province1"))
                .select(o -> o.columnAll().columnIgnore("license.extra"))
                .toList();
        Assert.assertEquals(province2.toString(), "[ValueCompany(id=my1, name=myCompany1, address=ValueCompanyAddress(province=province1, city=city1, area=area1), license=ValueCompanyLicense(licenseNo=license1, licenseDeadline=2023-01-01T00:00, extra=null))]");
//
//        List<ValueCompany> province22 = easyEntityQuery.queryable(ValueCompany.class)
//                .where(o -> o.address().province().eq("province1"))
//                .select(o->o.columnAll().columnIgnore(x->x.getLicense().getExtra()))
//                .toList();
//        Assert.assertEquals(province22.toString(),"[ValueCompany(id=my1, name=myCompany1, address=ValueCompanyAddress(province=province1, city=city1, area=area1), license=ValueCompanyLicense(licenseNo=license1, licenseDeadline=2023-01-01T00:00, extra=null))]");

        List<ValueCompany> province4 = easyQueryClient.queryable(ValueCompany.class)
                .where(o -> o.eq("address.province", "province1"))
                .select(o -> o.column("address").column("license.licenseNo"))
                .toList();

        Assert.assertEquals(province4.toString(), "[ValueCompany(id=null, name=null, address=ValueCompanyAddress(province=province1, city=city1, area=area1), license=ValueCompanyLicense(licenseNo=license1, licenseDeadline=null, extra=null))]");
//        List<ValueCompany> province44 = easyEntityQuery.queryable(ValueCompany.class)
//                .where(o -> o.address().province().eq("province1"))
//                .select(v -> {
//                    new ValueCompany()
//                })
//                .select(o->o.column(ValueCompany::getAddress).column(x->x.getLicense().getLicenseNo()))
//                .toList();
//
//        Assert.assertEquals(province44.toString(),"[ValueCompany(id=null, name=null, address=ValueCompanyAddress(province=province1, city=city1, area=area1), license=ValueCompanyLicense(licenseNo=license1, licenseDeadline=null, extra=null))]");

        easyEntityQuery.updatable(company).executeRows();
        TrackManager trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        try {
            trackManager.begin();
            easyEntityQuery.addTracking(company);
            company.getLicense().getExtra().setLicenseContent("it++++1");
            easyEntityQuery.updatable(company).executeRows();


            String s = easyEntityQuery.queryable(ValueCompany.class)
                    .where(o -> o.id().eq("my1"))
                    .selectColumn(s1 -> s1.license().extra().licenseContent())
                    .singleOrNull();
            Assert.assertNotNull(s);
            Assert.assertEquals("it++++1", s);
        } finally {
            trackManager.release();
        }


        {

            ValueCompanyDTO province12 = easyEntityQuery.queryable(ValueCompany.class)
                    .where(o -> o.address().province().eq("province1"))
                    .select(ValueCompanyDTO.class)
                    .firstOrNull();
            Assert.assertNotNull(province12);
            Assert.assertNotNull(province12.getAddress());
            Assert.assertNotNull(province12.getAddress().getProvince());
        }
    }
}
