import request from '@/utils/request'

export default{
  getAllMenu(){
    return request({
      url: '/menu',
      method: 'get'
    });
  },

  addMenu(conversation) {
    return request({
        url: '/menu',
        method: 'post',
        data: conversation
    });
},

updateMenu(conversation) {
  return request({
      url: '/menu',
      method: 'put',
      data: conversation
  });
},

  deleteMenuById(id) {
    return request({
        url: `/menu/${id}`,
        method: 'delete'
    });
},

}
