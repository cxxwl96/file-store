```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <link rel="stylesheet" href="index.css">
    <script src="vue.min.js"></script>
    <script src="index.js"></script>
    <style>
        .header {
            position: fixed;
            top: 0;
            width: 100%;
            height: 40px;
            line-height: 40px;
            background-color: white;
            box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2);
        }

        .header .title {
            width: 80%;
            display: inline-block;
            overflow: hidden;
            white-space: nowrap;
            text-overflow: ellipsis;
        }

        .header .tool {
            display: inline-block;
            position: fixed;
            right: 0;
            padding-right: 20px
        }

        .body {
            margin-top: 40px;
        }

        iframe {
            width: 100%;
            height: calc(100vh - 44.5px);
        }
    </style>
</head>
<body style="margin: 0">
<div id="app">
    <div class="header">
        <div class="title">【任务编号】任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题任务标题</div>
        <div class="tool">
            <el-button type="text" icon="el-icon-arrow-left" @click="back"></el-button>
            <el-button type="text" icon="el-icon-arrow-right" @click="go"></el-button>
            <el-button type="text" icon="el-icon-refresh" @click="refresh"></el-button>
        </div>
    </div>
    <div class="body" v-loading="loading">
        <iframe id="taskFrame" name="taskFrame" src="http://baidu.com" frameborder="0" allowfullscreen
                @load="loaded">
        </iframe>
    </div>
</div>
</body>
<script>
    new Vue({
        el: '#app',
        data: function () {
            return {
                loading: true
            }
        },
        created() {

        },
        methods: {
            back() {
                const taskFrame = document.getElementById('taskFrame');
                history.go(-1)
            },
            go() {
                const taskFrame = document.getElementById('taskFrame');
                history.go(1);
            },
            refresh() {
                const taskFrame = document.getElementById('taskFrame');
                window.open(taskFrame.src, 'taskFrame', '');
                // taskFrame.contentWindow.location.reload(true); // 出现跨域
            },
            loaded() {
                this.loading = false;
            }
        }
    });
</script>
</html>
```
