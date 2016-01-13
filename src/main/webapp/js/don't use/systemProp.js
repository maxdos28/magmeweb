var SystemProp={};
//�ӵ�����*.actionǰ��
SystemProp.appServerUrl="";
//�ӵ����о�̬��Դǰ��,����js,style�Լ���̬��ͼƬ,����/js/systemProp.js����
SystemProp.staticServerUrl="";
//ͳ�Ƶ�URL
SystemProp.statServerUrl="http://static.magme.com/magmeStat/";
//�ӵ��û�ͷ��ǰ��
SystemProp.profileServerUrl="http://static.magme.com/profiles";
//�ӵ��û��ϴ�����ʱͷ��ǰ��
SystemProp.profileServerUrlTmp="http://static.magme.com/profiles/tmp";
//��δ�õ�
SystemProp.sampleServerUrl="http://static.magme.com/samplefiles";
//�ӵ���ǩͼƬ��ǰ��
SystemProp.tagServerUrl="http://static.magme.com/tags";
//�ӵ���־��ͼƬ��SWFǰ��
SystemProp.magServerUrl="http://static.magme.com/pdfprofile";
//��Flash����
SystemProp.getUrl=function(param){
var url=eval('SystemProp.'+param);
return url;
}; 