/**
 * 
 */

var menuData = [
    {
        id: 'sys',
        name: '系统管理',
        isLeaf: false,
        conCls: 'el-icon-search',
        children: [
            {
                id: 'user',
                name: '用户管理',
                iconCls: 'el-icon-search',
                path: '/user/userIndex',
                isLeaf: true
            },
            {
                id: 'role',
                name: '角色管理',
                iconCls: '',
                path: '/role/index',
                isLeaf: true
            }
        ]
    }
]