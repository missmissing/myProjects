function showProcessWindow(processDefinitionId,activityId)
{
	$('#processWindow').window('open');
	$('#processImg').attr('src','activiti_getProcessImage.action?processDefinitionId='+processDefinitionId+'&activityId='+activityId);
}