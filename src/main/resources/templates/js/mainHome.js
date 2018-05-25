$(document).ready(function () {
    $("#htftpGetOperationFormSubmit").unbind("click");
    //上传或下载文件model框
    $("#getTftpServer").unbind('click').click(function () {
        //		var ifr = document.getElementById('Soft_Iframe');
        //		var win = ifr.window || ifr.contentWindow;
        //		win.FilterSearch();
        $("#htftpGetbtn").click();
    });
    $("#putTftpServer").unbind('click').click(function () {
        //		var ifr = document.getElementById('Soft_Iframe');
        //		var win = ifr.window || ifr.contentWindow;
        //		win.FilterSearch();
        $("#htftpPutbtn").click();
    });
    function randomNumber(min, max) {
        return Math.floor(Math.random() * (max - min + 1) + min);
    };

    function generateCaptcha() {
        $('#captchaOperation').html([randomNumber(1, 10), '+', randomNumber(1, 10), '='].join(' '));
    };
    generateCaptcha();
    //上传model框校验
    $('#htftpPutOperationForm').bootstrapValidator({
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            clientInterfaceIpValue: {
                message: 'The username is not valid',
                validators: {
                    otEmpty: {
                        message: 'Client ip Can not be empty'
                    },
                    stringLength: {
                        min: 6,
                        max: 15,
                        message: 'Client ip length 6-15'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: 'Client ip Only accept numbers and letters '
                    }
                }
            },
            clientInterfacePortValue: {
                validators: {
                    regexp: {
                        regexp: /^[0-9]+$/,
                        message: 'Client Port Only accept numbers '
                    },
                    notEmpty: {
                        message: 'Client port Can not be empty'
                    }
                }
            },
            remoteFileValue: {
                validators: {
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: 'remote file Only accept numbers and letters '
                    },
                    notEmpty: {
                        message: 'remote file port Can not be empty'
                    }
                }
            },
            blockSizeValue: {
                validators: {
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: 'block size Only accept numbers and letters '
                    },
                    notEmpty: {
                        message: 'block size port Can not be empty'
                    }
                }
            },
            captcha: {
                validators: {
                    callback: {
                        message: '验证码错误',
                        callback: function (value, validator) {
                            var items = $('#captchaOperation').html().split(' '),
                                sum = parseInt(items[0]) + parseInt(items[2]);
                            return value == sum;
                        }
                    }
                }
            }
        }
    })
        .on('error.form.bv', function (e) {
            var $form = $(e.target),
                bootstrapValidator = $form.data('bootstrapValidator');
            if (!bootstrapValidator.isValidField('captcha')) {
                generateCaptcha();
            }
        });
    $("#htftpPutOperationFormSubmit").click(function () {
        $('#htftpPutOperationForm').data('bootstrapValidator').validate();
        if ($('#htftpPutOperationForm').data("bootstrapValidator").isValid()) {
            var blockSizeValue = document.getElementById('blockSizeValue').value;
            var remoteFileValue = document.getElementById('remoteFileValue').value;
            var clientInterfacePortValue = document.getElementById('clientInterfacePortValue').value;
            var clientInterfaceIpValue = document.getElementById('clientInterfaceIpValue').value;
            $.ajax({
                url: "/DView8-TFTP-Server/putTftpServer",
                type: "get",
                dataType: "json",
                data: {
                    "blockSizeValue": blockSizeValue,
                    "remoteFileValue": remoteFileValue,
                    "clientInterfacePortValue": clientInterfacePortValue,
                    "clientInterfaceIpValue": clientInterfaceIpValue
                },
                success: function (data) {
                    switch (data.code) {
                        case "100":
                            $("#myModal").modal('hide');
                            alert("发送成功！");
                            break;
                    }
                }
            });
        } else {
            alert("校验失败")
        }


    });
    $("#putTftpServerCommint").click(function () {
        var blockSizeValue = document.getElementById('blockSizeValue').value;
        var remoteFileValue = document.getElementById('remoteFileValue').value;
        var clientInterfacePortValue = document.getElementById('clientInterfacePortValue').value;
        var clientInterfaceIpValue = document.getElementById('clientInterfaceIpValue').value;
        var currentDirectoryValue = document.getElementById('currentDirectoryValue').value;
        $.ajax({
            url: "/DView8-TFTP-Server/putTftpServer",
            type: "get",
            dataType: "json",
            data: {
                "clientInterfaceIpValue": clientInterfaceIpValue
            },
            success: function (data) {
                switch (data.code) {
                    case "200":
                        $("#myModal").modal('hide');
                        alert("发送成功！");
                        break;
                }
            }
        });

    });
});
$("#localFileValue").fileinput({

    language: 'zh', //设置语言

    uploadUrl: "../upLoad", //上传的地址
    //allowedPreviewTypes : [ 'image' ],//image
    //allowedFileExtensions: ['jpg', 'gif', 'png',''], //接收的文件后缀

    //uploadExtraData:{"id": 1, "fileName":'123.mp3'},

    uploadAsync: true, //默认异步上传

    showUpload: true, //是否显示上传按钮

    showRemove: true, //显示移除按钮

    showPreview: true, //是否显示预览

    showCaption: true, //是否显示标题

    browseClass: "btn btn-primary", //按钮样式

    dropZoneEnabled: true, //是否显示拖拽区域

    //minImageWidth: 50, //图片的最小宽度

    //minImageHeight: 50,//图片的最小高度

    //maxImageWidth: 1000,//图片的最大宽度

    //maxImageHeight: 1000,//图片的最大高度

    //maxFileSize:0,//单位为kb，如果为0表示不限制文件大小

    //minFileCount: 0,

    maxFileCount: 10, //表示允许同时上传的最大文件个数

    enctype: 'multipart/form-data',

    validateInitialCount: true,

    previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",

    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

}).on("fileuploaded", function (event, data, previewId, index) {

});

$("#file-5").fileinput({

    language: 'zh', //设置语言

    uploadUrl: "<%=basePath%>/commodity/addCommodityPic", //上传的地址
    //allowedPreviewTypes : [ 'image' ],//image
    //allowedFileExtensions: ['jpg', 'gif', 'png',''], //接收的文件后缀

    //uploadExtraData:{"id": 1, "fileName":'123.mp3'},

    uploadAsync: true, //默认异步上传

    showUpload: true, //是否显示上传按钮

    showRemove: true, //显示移除按钮

    showPreview: true, //是否显示预览

    showCaption: true, //是否显示标题

    browseClass: "btn btn-primary", //按钮样式

    dropZoneEnabled: true, //是否显示拖拽区域

    //minImageWidth: 50, //图片的最小宽度

    //minImageHeight: 50,//图片的最小高度

    //maxImageWidth: 1000,//图片的最大宽度

    //maxImageHeight: 1000,//图片的最大高度

    //maxFileSize:0,//单位为kb，如果为0表示不限制文件大小

    //minFileCount: 0,

    maxFileCount: 10, //表示允许同时上传的最大文件个数

    enctype: 'multipart/form-data',

    validateInitialCount: true,

    previewFileIcon: "<iclass='glyphicon glyphicon-king'></i>",

    msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",

}).on("fileuploaded", function (event, data, previewId, index) {
    alert()
});