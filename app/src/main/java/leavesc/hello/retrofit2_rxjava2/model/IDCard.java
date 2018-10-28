package leavesc.hello.retrofit2_rxjava2.model;

/**
 * 作者：叶应是叶
 * 时间：2018/10/28 10:25
 * 描述：
 */
public class IDCard {

    private String area;

    private String sex;

    private String birthday;

    private String verify;

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    @Override
    public String toString() {
        return "IDCard{" +
                "area='" + area + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", verify='" + verify + '\'' +
                '}';
    }
}
