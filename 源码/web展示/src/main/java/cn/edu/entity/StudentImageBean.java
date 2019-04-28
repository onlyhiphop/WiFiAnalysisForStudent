package cn.edu.entity;

import java.io.Serializable;

/**
 * @author lbc
 * @description: TODO
 * @date 2019/3/31 16:12
 */
public class StudentImageBean implements Serializable{
    private String mac;
    private String hobbies;
    private String favorite_stay_place;
    private String favorite_go_place;
    private String consumption_ability;

    public StudentImageBean() {
    }

    public StudentImageBean(String mac, String hobbies, String favorite_stay_place, String favorite_go_place, String consumption_ability) {
        this.mac = mac;
        this.hobbies = hobbies;
        this.favorite_stay_place = favorite_stay_place;
        this.favorite_go_place = favorite_go_place;
        this.consumption_ability = consumption_ability;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getFavorite_stay_place() {
        return favorite_stay_place;
    }

    public void setFavorite_stay_place(String favorite_stay_place) {
        this.favorite_stay_place = favorite_stay_place;
    }

    public String getFavorite_go_place() {
        return favorite_go_place;
    }

    public void setFavorite_go_place(String favorite_go_place) {
        this.favorite_go_place = favorite_go_place;
    }

    public String getConsumption_ability() {
        return consumption_ability;
    }

    public void setConsumption_ability(String consumption_ability) {
        this.consumption_ability = consumption_ability;
    }

    @Override
    public String toString() {
        return "StudentImageBean{" +
                "mac='" + mac + '\'' +
                ", hobbies='" + hobbies + '\'' +
                ", favorite_stay_place='" + favorite_stay_place + '\'' +
                ", favorite_go_place='" + favorite_go_place + '\'' +
                ", consumption_ability='" + consumption_ability + '\'' +
                '}';
    }
}
