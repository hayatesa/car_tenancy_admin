function ConvertJSONDateToJSDate(jsonDate) {
        try {
            var date = new Date(parseInt(jsonDate.replace("/Date(", "").replace(")/", ""), 10));
            var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
            var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
            var milliseconds = date.getMilliseconds();
            milliseconds = myPow(10, (3 - milliseconds.toString().length)).toString().substr(1) + milliseconds.toString();
            return date.getFullYear() + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
        } catch (ex) {
            return "";
        }
        function myPow(dx, dy) {
            var r = 1;
            while (dy != 0) {
                var b = dy & 1; //取最末尾的一位数,也可以判断奇偶数，奇数：1，偶数：0
                if (b) {//如果最末尾的数是1,储存有效值
                    r *= dx;
                }
                dx *= dx; //这里即完成了x^(2^(n-1)*i)的计算
                dy >>= 1; //右位移去掉末尾1位,也可以看成是除以2取整数
            }
            return r;
        }
    }