```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="index.css">
    <script src="vue.min.js"></script>
    <script src="index.js"></script>
</head>
<body>
<div id="app">
    <p>原链接：{{result.link}}</p>
    <p>生成的链接：{{result.formatLink}}</p>
    <p>显示的链接：<span v-html="result.viewLink"></span></p>
    <p>替换了{{result.formatCount}}个参数</p>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data: function () {
            // TODO link、rule需要增加类型约束
            function formatLink(link, rules) {
                const result = {
                    link: link,
                    formatLink: link,
                    formatCount: 0,
                    viewLink: link,
                };
                link = encodeURI(link);
                const ruleKeys = rules.map(rule => rule.key);
                if (link) {
                    const start = link.indexOf("?");
                    if (start > 0) {
                        let newLink = link.substring(0, start + 1);
                        // 截取 ? 之后的字符串
                        const str = link.substring(start + 1, link.length);
                        // 按 & 分割
                        const formatPvStrArr = []; // 例如：["uuid=${uuid}", "sjuser=123"]
                        const viewPvStrArr = []; // 例如：["<span style='color: red; font-weight: bold'>uuid=${uuid}</span>", "sjuser=123"]
                        const pvStrArr = str.split('&');
                        let formatCount = 0; // 替换的个数
                        pvStrArr.forEach(pvStr => {
                            // 按 = 分割
                            const pvArr = pvStr.split("=");
                            // 参数名是否存在于rule中
                            if (pvArr.length === 2 && ruleKeys.filter(key => key === pvArr[0]).length > 0) {
                                formatCount++;
                                formatPvStrArr.push(pvArr[0] + '=${' + pvArr[0] + '}');
                                viewPvStrArr.push("<span style='color: red; font-weight: bold'>" + pvArr[0] + '=${' + pvArr[0] + '}' + "</span>");
                            } else {
                                formatPvStrArr.push(pvStr);
                                viewPvStrArr.push(pvStr);
                            }
                        });
                        // 拼接新的链接
                        result.formatLink = newLink + formatPvStrArr.join("&");
                        result.formatCount = formatCount;
                        result.viewLink = newLink + viewPvStrArr.join("&");
                    }
                }
                return result;
            }

            let link = 'http://www.baidu.com?uuid=123456&sjuser=abcde';
            const rules = [
                {key: 'uuid', value: ''},
                {key: 'sjuser', value: ''},
            ];
            const result = formatLink(link, rules);
            if (result.formatCount !== rules.length) {
                this.$message.error('该公司有' + rules.length + '个参数名，但是只替换了' + result.formatCount + '个参数，请检查链接是否正确');
            }
            return {
                result,
            }
        },
        created() {

        },
        methods: {}
    });
</script>
</html>
```

```nginx

#user  nobody;
worker_processes  1;

#error_log  logs/error.log;
#error_log  logs/error.log  notice;
#error_log  logs/error.log  info;

#pid        logs/nginx.pid;


events {
    worker_connections  1024;
}


http {
    include       mime.types;
    default_type  application/octet-stream;

    #log_format  main  '$remote_addr - $remote_user [$time_local] "$request" '
    #                  '$status $body_bytes_sent "$http_referer" '
    #                  '"$http_user_agent" "$http_x_forwarded_for"';

    #access_log  logs/access.log  main;

    sendfile        on;
    #tcp_nopush     on;

    #keepalive_timeout  0;
    keepalive_timeout  65;

    #gzip  on;

    server {
    	listen 8080; 
    	location / {
    		root path;
    	}

    }
    server {
    	listen 4000; 
    	location / {
    		root path;
    	}

    }
    server {
    	listen 10086; 
    	location / {
    		root path;
    	}

    }
    server {
    	listen 10087; 
    	location / {
    		root path;
    	}
    }
    server {
    	listen 10088; 
    	location / {
    		root path;
    	}
    }

}
```
