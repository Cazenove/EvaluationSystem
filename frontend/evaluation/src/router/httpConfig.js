const http = "http://127.0.0.1:8082"
// 接口URL

const apiList = {
	
	testGet: 'http://www.dzyong.top:3005/yiqing/area',
	testPost: 'http://49.235.26.253:8082/publishQuestion',
	
	login: http + '/user/login',
	register: http + '/user/register',
	
	userUpdate: http + '/user/update',
	userEvaluationOuterList: http + '/user/evaluation/outer/list',
	userEvaluationOuter: http + '/user/evaluation/outer',
	userEvaluationOuterSubmit: http + '/user/evaluation/outer/submit',
	
	userEvaluationInnerList: http + '/user/evaluation/inner/list',
	userEvaluationInner: http + '/user/evaluation/inner',
	userEvaluationInnerSubmit: http + '/user/evaluation/inner/submit',
	
	userGroupDetails: http + '/user/group/details',
	userGroupUserdetails: http + '/user/group/userdetails',
	
	adminUserCreate: http + '/admin/user/create',
	adminUserDelete: http + '/admin/user/delete',
	adminUserUpdate: http + '/admin/user/update',
	adminUsetList: http + '/admin/user/list',
	
	adminAssistantCreate: http + '/admin/assistant/create',
	adminAssistantDelete: http + '/admin/assistant/delete',
	adminAssistantUpdate: http + '/admin/assistant/update',
	adminAssistantList: http + '/admin/assistant/list',
	
	adminClassCreate: http + '/admin/class/create',
	adminClassDelete: http + '/admin/class/delete',
	adminClassUpdate: http + '/admin/class/update',
	adminClassList: http + '/admin/class/list',
	adminClassEnd: http + '/admin/class/end',
	
	
	adminEvaluationCreate: http + '/admin/evaluation/create',
	adminEvaluationDelete: http + '/admin/evaluation/delete',
	adminEvaluationUpdate: http + '/admin/evaluation/update',
	adminEvaluationDetails: http + '/admin/evaluation/details',
	
	adminSuggestionCreate: http + '/admin/suggestion/create',
	adminSuggestionDelete: http + '/admin/suggestion/delete',
	adminSuggestionUpdate: http + '/admin/suggestion/update',
	adminSuggestionList: http + '/admin/suggestion/list',
	
	adminGroupScoreList: http + '/admin/group/score/list',
	adminGroupScoreFinal: http + '/admin/group/score/final',
	adminUserScoreList: http + '/admin/user/score/list',
	adminUserScoreFinal: http + '/admin/user/score/final',
	
	adminTeamList: http + '/admin/team/list',
	
	userTeamUpdate: http + '/user/team/update',
	
	adminEvaluationOuterSubmit: http + '/admin/evaluation/outer/submit',
	adminEvaluationInnerSubmit: http + '/admin/evaluation/inner/submit',
	
	teamList: http + '/register/team'
}

export default apiList