import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Atm {
    private ArrayList<Account> accounts =new ArrayList<>();
    private Scanner sc=new Scanner(System.in);
    private Account loginAcc;

    public void start() {
        while (true) {
            System.out.println("欢迎来到小刘银行系统");
            System.out.println("1.用户登录");
            System.out.println("2.用户开户");
            System.out.println("请选择：");
            String command = sc.next();
            switch (command) {
                case "1":
                    login();
                    break;
                case "2":
                    createAccount();
                    break;
                default:
                    System.out.println("没有此操作，请重新输入！");
                    break;
            }
        }
    }
    private void login()
    {
        System.out.println("===系统登录界面===");
        if(accounts.size()==0)
        {
            System.out.println("当前系统中没有任何账户，请先开户后再来登录！");
            return ;
        }
        while (true) {
            System.out.println("请输入你的卡号：");
            String cardId=sc.next();
            Account acc=findAccountByCardId(cardId);
            if(acc==null){
                System.out.println("您输入的卡号不存在，请重新输入");
            }else{
                System.out.println("请您输入登录密码：");
                String psw= sc.next();
                if(acc.getPassword().equals(psw))
                {
                    loginAcc=acc;
                    System.out.println("恭喜您，"+acc.getUsername()+"成功登录了系统，您的卡号是："+acc.getCardId());
                    showCommand();
                    return;
                }else{
                    System.out.println("您输出的密码不正确，请重新输入！");
                }
            }
        }
    }
    private void showCommand()
    {
        while (true) {
            System.out.println(loginAcc.getUsername() + "您可以选择如下功能进行账户的处理====");
            System.out.println("1、查询账户");
            System.out.println("2、存款");
            System.out.println("3、取款");
            System.out.println("4、转账");
            System.out.println("5、密码修改");
            System.out.println("6、退出");
            System.out.println("7、注销当前账户");
            System.out.println("请选择：");
            int command = sc.nextInt();
            switch (command){
                case 1:
                    // 查询当前账户
                    showLoginAccount();
                    break;
                case 2:
                    // 存款
                    depositMoney();
                    break;
                case 3:
                    // 取款
                    drawMoney();
                    break;
                case 4:
                    // 转账
                    transferMoney();
                    break;
                case 5:
                    // 密码修改
                    updatePassWord();
                    return;// 跳出并结束当前方法
                case 6:
                    // 退出
                    System.out.println(loginAcc.getUsername() + "您退出系统成功！");
                    return; // 跳出并结束当前方法
                case 7:
                    // 注销当前登录的账户
                    if(deleteAccount()){
                        // 销户成功了，回到欢迎界面
                        return;
                    }
                    break;
                default:
                    System.out.println("您当前选择的操作是不存在的，请确认~~");
            }
        }
    }
    private boolean deleteAccount()
    {
        System.out.println("==进行销户操作==");
        // 1、问问用户是否确定要销户啊
        System.out.println("请问您确认销户吗？y/n");
        String command = sc.next();
        switch (command) {
            case "y":
                // 确实要销户
                // 2、判断用户的账户中是否有钱：loginAcc
                if(loginAcc.getMoney() == 0) {
                    // 真的销户了
                    accounts.remove(loginAcc);
                    System.out.println("您好，您的账户已经成功销户~~");
                    return true;
                }else {
                    System.out.println("对不起，您的账户中存钱金额，不允许销户~~");
                    return false;
                }
            default:
                System.out.println("好的，您的账户保留！！");
                return false;
        }
    }
    private void updatePassWord()
    {
        System.out.println("====修改密码操作====");
        while (true) {
            System.out.println("请输入原密码：");
            String psw= sc.next();
            if(loginAcc.getPassword().equals(psw))
            {
                while (true) {
                    System.out.println("请输入新密码:");
                    String newPsw=sc.next();
                    System.out.println("请再次输入新密码:");
                    String okPsw=sc.next();
                    if(newPsw.equals(okPsw))
                    {
                        loginAcc.setPassword(okPsw);
                        System.out.println("修改密码成功！");
                        return;
                    }
                    else{
                        System.out.println("两次输入的密码不一致！");
                    }
                }
            }else{
                System.out.println("你输入的原密码不正确，请重新输入");
            }
        }
    }
    private void transferMoney()
    {
        System.out.println("====转账操作====");
        if(accounts.size()<2)
        {
            System.out.println("当前系统中只有你一个账户，无法为其他账户转账~~");
            return;
        }
        if(loginAcc.getMoney()==0)
        {
            System.out.println("您的账户中没钱，无法转账");
            return;
        }
        while (true) {
            System.out.println("请输入对方账户的卡号：");
            String otherId= sc.next();
            Account acc=findAccountByCardId(otherId);
            if(acc==null)
            {
                System.out.println("您输入的对方卡号不存在");
            }else
            {
                String name="*"+acc.getUsername().substring(1);
                System.out.println("请您输入【"+name+"】的姓氏：");
                String preName=sc.next();
                if(acc.getUsername().startsWith(preName))
                {
                    while (true) {
                        System.out.println("请输入转账的金额：");
                        double money= sc.nextDouble();
                        if(loginAcc.getMoney()>=money)
                        {
                            loginAcc.setMoney(loginAcc.getMoney()-money);
                            acc.setMoney(acc.getMoney()+money);
                            System.out.println("您已经转账成功！");
                            return;
                        }
                        else{
                            System.out.println("您的余额不足，您最多可以转"+loginAcc.getMoney()+"元");
                        }
                    }
                }else{
                    System.out.println("您输入的姓氏有问题！");
                }
            }
        }
    }
    private void depositMoney()
    {
        System.out.println("====存钱操作====");
        System.out.println("请您输入你要存入的金额：");
        double money=sc.nextDouble();
        loginAcc.setMoney(loginAcc.getMoney()+money);
        System.out.println("您存入金额："+money+"元成功,存入后金额是："+loginAcc.getMoney());
    }
    private void drawMoney()
    {
        System.out.println("====取钱操作====");
        System.out.println("请您输入你要取走的金额：");
        double money=sc.nextDouble();
        if(money<=loginAcc.getMoney())
        {
           if(money>loginAcc.getLimit()){
               System.out.println("您当前取款金额超过了每次限额，您每次最多可取：" + loginAcc.getLimit());
           }else{
               loginAcc.setMoney(loginAcc.getMoney()-money);
               System.out.println("您取款：" + money + "成功，取款后您剩余：" + loginAcc.getMoney());
           }
        }
        else{
            System.out.println("余额不足，您的账户中的余额是：" + loginAcc.getMoney());
        }
    }
    private void showLoginAccount()
    {
        System.out.println("==当前您的账户信息如下：==");
        System.out.println("卡号：" + loginAcc.getCardId());
        System.out.println("户主：" + loginAcc.getUsername());
        System.out.println("性别：" + loginAcc.getSex());
        System.out.println("余额：" + loginAcc.getMoney());
        System.out.println("每次取现额度：" + loginAcc.getLimit());
    }
    private void createAccount()
    {
        Account acc=new Account();
            System.out.println("----开户功能系统----");
            System.out.println("请输入你的姓名：");
            String username=sc.next();
            acc.setUsername(username);

            while (true) {
                System.out.println("请输入你的性别：");
                String sex=sc.next();
                if (sex.equals("男") || sex.equals("女")) {
                    acc.setSex(sex);
                    break;
                }else{
                    System.out.println("输入的性别只能是男或女！请重新输入！");
                }
            }
            while (true) {
                System.out.println("请输入你的密码：");
                String password=sc.next();
                System.out.println("请输入你的确认密码：");
                String okPassword=sc.next();
                if(password.equals(okPassword))
                {
                    acc.setPassword(okPassword);
                    break;
                }else{
                    System.out.println("两次输入的密码不一致，请重新输入！");
                }
            }
            System.out.println("请输入你的取现额度：");
            double limit= sc.nextDouble();
            acc.setLimit(limit);
            String cardId=createCardId();
            acc.setCardId(cardId);
            accounts.add(acc);
            System.out.println("恭喜"+acc.getUsername()+"开户成功！您的卡号是："+acc.getCardId());
        }
    private String createCardId()
    {
        while (true) {
            Random r=new Random();
            String cardId="";
            for(int i=0;i<8;i++)
            {
                int id= r.nextInt(10);
                cardId+=id;
            }
            Account acc=findAccountByCardId(cardId);
            if(acc==null)
            {
                return cardId;
            }
        }
    }
    private Account findAccountByCardId(String cardId){
        for (int i = 0; i < accounts.size(); i++) {
            Account account=accounts.get(i);
            if(account.getCardId().equals(cardId))
            {
                return account;
            }
        }
        return null;
    }
}
