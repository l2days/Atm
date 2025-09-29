public class Account {
    private String cardId;
    private String username;
    private String password;
    private String sex;
    private double money;
    private double limit;

    public Account() {
    }

    public Account(String cardId, String username, String password, String sex, double money, double limit) {
        this.cardId = cardId;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.money = money;
        this.limit = limit;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUsername() {
        return username+(sex.equals("男") ? "先生": "女士");
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
}
