var ctx = "";//项目部署的工程名
var SysResourceList;
var SysResourceEdit;
var SysResourceForm;

//其它组件
var parentResource;
//选择图片对话框
var SysResourceChooseIcon;
//图片textbox
var iconText;

var SysResource = {
    URL: {
        addInit: function () {
            return ctx + "/sys/sysPermission/addInit";
        },
        editInit: function () {
            return ctx + "/sys/sysPermission/addInit";
        },
        list: function () {
            return ctx + "/sys/sysPermission/list";
        },
        add: function () {
            return ctx + "/sys/sysPermission/add";
        },
        edit: function () {
            return ctx + "/sys/sysPermission/edit";
        },
        delete: function (ids) {
            return ctx + "/sys/sysPermission/delete?ids=" + ids;
        },
        get: function (id) {
            return ctx + "/sys/resources/" + id;
        }
    },
    input: {
        init: function (ct) {
            ctx = ct;
        },
        submitForm: function () {
            // submit the form
            SysResourceForm.submit();
        },
        close: function () {
            SysResourceEdit.dialog('close');
        },
        chooseIcon: function () {
            //使用全局弹窗
            globalWindow.dialog({
                title:'选择图标',
                width:605,
                height:400,
                modal:true,
                href: SysResource.URL.iconUI(),
                cache:false
            });
        },
        setIcon: function (value) {
            iconText.textbox("setValue", value);
            globalWindow.dialog("close");
        }
    },
    list: {
        init: function (ct) {
            ctx = ct;
            SysResource.list.initList();
        },
        initList: function () {
            $("#SysResourceList").treegrid({
                url: SysResource.URL.list(),
                method: 'get',
                pagination: true,
                pageSize: 30,
                toolbar: '#SysResourceToolbar',//SysResource.list.toolbar,
                singleSelect: false,
                collapsible: false,
                idField: 'id',
                treeField: 'name',
                parentField: 'pid',//自定义属性
                columns: [[
                    {field: 'ck', checkbox: true},
                    {field: 'id', title: '主键', hidden: true},
                    {field: 'name', title: '资源名称', width: '10%', hidden: false},
                    {field: 'vurl', title: '资源路径', width: '10%', hidden: false},
                    {
                        field: 'icon', title: '资源图标', width: '10%', hidden: false,
                        formatter: function (value, row, index) {

                            return value;
                        }
                    },
                    {field: 'sort_order', title: '排序', width: '7.917%', hidden: false},
                    {
                        field: 'status',
                        title: '状态',
                        width: '7.917%',
                        hidden: false,
                        formatter: function (value, row, index) {
                            return value == 1 ? '启用' : '停用';
                        }
                    }
                ]],
                //对返回的数据进行处理,便于显示树形结构
                loadFilter: function (data, parentId) {
                    var opt = $(this).data().treegrid.options;
                    var parentField;//获取自定义属性
                    if (opt.parentField) {
                        parentField = opt.parentField;
                        var jsonStr = JSON.stringify(data); //可以将json对象转换成json字符串
                        jsonStr = jsonStr.replace(new RegExp(parentField, "gm"), "_parentId");
                        return JSON.parse(jsonStr); //可以将json字符串转换成json对象
                    }
                },
                //设置选中事件，清除之前的行选择
                onClickRow: function (row) {
                    SysResourceList.treegrid("unselectAll");
                    SysResourceList.treegrid("selectRow",row.id);
                },
            });
        },
        getSelectionsIds: function () {
            var sels = SysResourceList.treegrid("getSelections");
            var ids = [];
            for (var i in sels) {
                ids.push(sels[i].id);
            }
            ids = ids.join(",");
            return ids;
        },
        //增
        add: function () {
            var p = xlauch.addRow({
                title : '新增权限',
                href : SysResource.URL.addInit(),
                width : 605,
                //isIframe: true ,
                height: 400
            });
        },
        //改
        edit: function () {
            var r = SysResourceList.treegrid("getSelections");
            if (r.length < 1) {
                $.messager.alert("对话框", "至少选择一行");
                return;
            }

            if (r.length > 1) {
                $.messager.alert("对话框", "只能选择一行");
                return;
            }

            var p = xlauch.editRow({
                title : '修改链接',
                href : '/demo/updateInit',
                //url :'/demo/update',
                width : 350,
                height: 250,
                row : r/*,
                 onLoad : function(){
                 var row = p.getRow() ;
                 var f = p.find('form');
                 f.form('load', row);
                 }*/
            });
        },
        //删
        delete: function () {

            xlauch.deleteRow({
                dataGrid : "SysResourceList",
                pk : 'id',
                url: "/demo/deleteBgAppLinkBatch"
            });
        },
        //刷新
        reload: function () {
            SysResourceList.treegrid("reload");
        },
        clearSelectionsAndChecked: function () {
            SysResourceList.treegrid("clearChecked");
            SysResourceList.treegrid("clearSelections");
        },
        //搜索
        search: function () {
            var searchName = [];
            var searchValue = [];
            $("input[lang='searchSysResource']").each(function (i) {
                searchName[i] = $(this).attr("name");
                searchValue[i] = $(this).val();
            });

            var queryParamsArr = [];
            for (var i = 0; i < searchName.length; i++) {
                queryParamsArr.push(searchName[i] + ":'" + searchValue[i] + "'")
            }
            var queryParams = "{" + queryParamsArr.join(",") + "}";

            SysResourceList.treegrid({
                queryParams: eval('(' + queryParams + ')'),
                onLoadSuccess: function (data) {
                    //回显搜索内容
                    $("input[lang='searchSysResource']").each(function (i) {
                        $(this).val(searchValue[i]);
                    });
                }
            });
        },
        //清空
        clear: function () {
            $("input[lang='searchSysResource']").each(function (i) {
                $(this).val("");
            });
        },
        //折叠
        collapseAll: function () {
            var roots = SysResourceList.treegrid("getRoots");
            for (var i in roots) {
                SysResourceList.treegrid("collapseAll", roots[i].id);
            }
        },
        //展开
        expandAll: function () {
            var roots = SysResourceList.treegrid("getRoots");
            for (var i in roots) {
                SysResourceList.treegrid("expandAll", roots[i].id);
            }
        },
    }
}
