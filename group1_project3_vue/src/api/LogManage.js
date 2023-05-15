import request from '@/utils/request'

export default {
    getLogList() {
        return request({
            url: '/sys/log/list',
            method: 'get'
        });
    },
    addLog(log) {
        return request({
            url: '/sys/log/add',
            method: 'post',
            data: log
        });
    },
    updateLog(log) {
        return request({
            url: '/sys/log/update',
            method: 'put',
            data: log
        });
    },
    deleteLogById(id) {
        return request({
            url: `/sys/log/${id}`,
            method: 'delete'
        });
    },
}