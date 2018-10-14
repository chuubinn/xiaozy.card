<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <script src="http://res.wx.qq.com/open/js/jweixin-1.4.0.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    //获取当前支付的ip地址
    <script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
    <title>Title</title>
</head>
<body>
<div>
    index
    <img src="http://img3.imgtn.bdimg.com/it/u=496641143,3869583512&fm=200&gp=0.jpg" alt="png"/>
    <!--<img src="../static/123.jpg" alt="png" />-->
</div>
</body>
<script>
    //封装获取url中的参数
    function getQueryString(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) return unescape(r[2]);
        return null;
    }

    //获取用户的openid
    var openid = getQueryString("openid");
    if (openid) {
        window.localStorage.setItem("openid", openid);
        alert("openid"+openid)
    }

    //获取当前支付的ip地址
    console.log(returnCitySN["cip"]);

    $(document).ready(function () {
        //生成随机字符串函数
        function randomString(len) {
            len = len || 32;
            var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678';
            /****默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1****/
            var maxPos = $chars.length;
            var pwd = '';
            for (i = 0; i < len; i++) {
                pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
            }
            return pwd;
        }

        //document.write(randomString(32));

        //设置随机字符串
        var nonceStr = randomString(32);
        //设置时间戳
        var timestamp = Math.floor(new Date().getTime() / 1000) + "";
        //获取当前页面完整 url
        var url = window.location.href;
        //获取当前发起支付的客户端ip地址(建议不要开启代理)
        var ip = returnCitySN["cip"];
        //向服务器发起请求获取config参数
        $.get("http://xiaozy.natapp1.cc/wechat/getConfig", {
            timeStamp: timestamp,
            nonceStr: nonceStr,
            url: url,
            openid: openid
        }, function (data, status) {
            console.log(data);
            alert("appId: " + data.appId + "\nStatus: " + status);
            alert("timestamp: " + data.timestamp + "\nStatus: " + status);
            alert("nonceStr: " + data.nonceStr + "\nStatus: " + status);
            alert("signature: " + data.signature + "\nStatus: " + status);

            wx.config({
                debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                appId: data.appId, // 必填，公众号的唯一标识
                timestamp: data.timestamp, // 必填，生成签名的时间戳
                nonceStr: data.nonceStr, // 必填，生成签名的随机串
                signature: data.signature,// 必填，签名
                jsApiList: ["chooseWXPay"], // 必填，需要使用的JS接口列表
                success: function (res) {
                    alert("config成功")
                },
                fail: function (res) {
                    alert("config失败")
                },
            });
            wx.ready(function () {
                //wx.startRecord();
                alert("ready");
                $.get("http://xiaozy.natapp1.cc/wechat/little/wePay", {
                    body: "test",//商品描述
                    device_info: "web",//设备号
                    out_trade_no: new Date().getTime()+"",//订单号(建议每次发送随机)
                    total_fee: "1", //1分钱
                    timeStamp: timestamp,//支付签名时间戳(10位数字)，注意微信jssdk中的所有使用timestamp字段均为小写
                    nonceStr: nonceStr, //支付签名随机串，不长于 32 位
                    spbill_create_ip: ip //客户端ip地址
                }, function (data, status) {
                    //发起支付
                    alert("package"+data.package);
                    alert("paySign"+data.paySign);
                    wx.chooseWXPay({
                        appId: "wxdfe8e9a69851a406", //appid
                        timestamp: timestamp,
                        nonceStr: nonceStr, // 支付签名随机串，不长于 32 位
                        package: data.package, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=\*\*\*）
                        signType: "MD5", // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
                        paySign: data.paySign, // 支付签名
                        success: function (res) {
                            // 支付成功后的回调函数
                            alert("支付成功")
                        },
                        fail: function (res) {
                            alert("支付失败")
                        },
                    });
                })

            });
        });


    });
</script>
</html>