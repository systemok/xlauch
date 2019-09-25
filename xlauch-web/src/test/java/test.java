import cn.hutool.core.lang.Console;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import org.junit.Test;

import java.util.HashMap;

/**
 * <p>
 * 类描述：
 * </p>
 *
 * @author huangxy
 * @version 0.1
 * @since 2017/12/21.
 */
public class test {

    private String URL = "https://activity.waimai.meituan.com/coupon/sharechannel/B2EA8E1ABA8B47EA82DB475BA17B517D?urlKey=24DD16EA8EC24D92BC9EFDADED6BF2B3";

    private String USERPHONE = "13859040142";

    @Test
    public void ddd() {
        //可以单独传入http参数，这样参数会自动做URL编码，拼接在URL中
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("url", URL);
        paramMap.put("mobile", "13859040142");
        String result3 = HttpUtil.post("http://192.168.1.207:3007/hongbao", paramMap);
        //String result3= HttpUtil.post("https://hongbao.xxooweb.com/hongbao", paramMap);
        System.out.println("" + result3);
    }


    /*const res = await post('/coupon/grabShareCoupon', {
        userPhone,
                channelUrlKey: data.channelUrlKey,
                urlKey: params.urlKey,
                dparam: data.dparam,
                originUrl: url,
                baseChannelUrlKey: '',
                uuid: '',
                platform: 11,
                partner: 162,
                riskLevel: 71
    }, {
        headers: {
            cookie: randomCookie(userPhone2)
        }
    })*/
    // 4201 需要验证码
    // 1006 该号码归属地暂不支持
    // 1 成功
    // 7003 已领过
    // 4000 抢光了
    // 7002 微信 cookie 不正确或失效
    // 7006 今日领取次数达达到上限
    // 4002 你已经抢过这个红包了
    // 4001 已过期（不知道是什么过期，我认为是红包，所以直接抛出了）
    // 4003 没领到（什么鬼）

    @Test
    public void testAdd() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("userPhone",USERPHONE);
        paramMap.put("channelUrlKey","");
        paramMap.put("urlKey",getUrlKey());
        paramMap.put("dparam","");
        paramMap.put("originUrl","");
        paramMap.put("baseChannelUrlKey","");
        paramMap.put("uuid","");
        paramMap.put("platform",11);
        paramMap.put("partner",162);
        paramMap.put("riskLevel",71);


        //链式构建请求
        String result2 = HttpRequest.post(URL)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T MicroMessenger) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36")
                .cookie("")
                .form(paramMap)
                .execute().body();
        Console.log(result2);
    }



    @Test
    public void aaaa() {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("urlKey","6D011F16DA554D51A9CEA747A214F69F");
        paramMap.put("channelUrlKey","B2EA8E1ABA8B47EA82DB475BA17B517D");

        String url = "https://activity.waimai.meituan.com/async/coupon/sharechannelredirect";

        //链式构建请求
        String result2 = HttpRequest.post(url)
                .header(Header.USER_AGENT, "Mozilla/5.0 (Linux; Android 5.0; SM-G900P Build/LRX21T MicroMessenger) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.23 Mobile Safari/537.36")
                .form(paramMap)
                .execute().body();

        Console.log(result2);
    }




    private String getUrlKey(){
        return "" ;
    }

}
