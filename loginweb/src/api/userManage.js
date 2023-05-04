import request from '@/utils/request'

export default {
    updatePassword(token, username, oldPassword, newPassword) {
        return request({
            url: '/user/password',
            method: 'post',
            params: {
                token,
                username,
                oldPassword,
                newPassword
            }
        });
    },
    getUserList(searchModel) {
        return request({
            url: '/user/list',
            method: 'get',
            params: {
                pageNo: searchModel.pageNo,
                pageSize: searchModel.pageSize,
                username: searchModel.username,
                phone: searchModel.phone,
                startTime: searchModel.startTime,
                endTime: searchModel.endTime,
            }
        });
    },
    addUser(user) {
        return request({
            url: '/user',
            method: 'post',
            data: user
        });
    },
    updateUser(user) {
        return request({
            url: '/user',
            method: 'put',
            data: user
        });
    },
    saveUser(user) {
        if (user.id == null && user.id == undefined) {
            return this.addUser(user);
        }
        return this.updateUser(user);
    },
    getUserById(id) {
        return request({
            //url: '/user/' + id,
            url: `/user/${id}`,
            method: 'get'
        });
    },
    deleteUserById(id) {
        return request({
            url: `/user/${id}`,
            method: 'delete'
        });
    },
}