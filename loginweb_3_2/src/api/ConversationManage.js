import request from '@/utils/request'

export default {
    getConvList() {
        return request({
            url: '/sys/conversation/list',
            method: 'get'
        });
    },
    getList(searchModel){
        return request({
          url: '/sys/conversation/list',
          method: 'get',
          params:{
            pageNo: searchModel.pageNo,
            pageSize: searchModel.pageSize,
            name: searchModel.name,
            id: searchModel.id,
            ip: searchModel.ip,
            port: searchModel.port,
            username: searchModel.username ,
          }
        });
      },
    addConv(conversation) {
        return request({
            url: '/sys/conversation/add',
            method: 'post',
            data: conversation
        });
    },
    updateConv(conversation) {
        return request({
            url: '/sys/conversation/update',
            method: 'put',
            data: conversation
        });
    },
    deleteConvById(id) {
        return request({
            url: `/sys/conversation/${id}`,
            method: 'delete'
        });
    },
}