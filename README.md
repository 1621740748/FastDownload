# FastDownload
java���̶߳������أ�֧�ֶϵ����������ļ��и�����  
GitHub Pages: [https://github.com/fengshangbin/FastDownload](https://github.com/fengshangbin/FastDownload)

# ����
- ���̶߳�������
- �ϵ�����
- ���ļ��и������

# �������
רע���غ��Ĺ��ܣ��ṩUI״̬�ص�������ȫ�棬ʹ�ü�

# ����
okhttp3(com.squareup.okhttp3), json(org.json.json)

# ��ʼ��
ʵ�ֳ־û��ӿڣ��Ա��ܻ������ؽ��ȣ�������AndroidΪ��
```
CacheInterface cache = new CacheInterface() {
    @Override
    public String read() {
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString("downloadBreakPoint","");
    }

    @Override
    public void write(String data) {
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("downloadBreakPoint", data);
        editor.commit();
    }
};
DownloadBreakPoint.setCacheInterface(cache);
```

# ����
������Ҫ���ص���Դ�б�
```
ArrayList<Resource> resources = new ArrayList<>();
String sourceURL = "https://csdnimg.cn/public/common/libs/jquery/jquery-1.9.1.min.js";
String savePath = IOUtils.getAppDir() + "/jq.js";
String md5 = "ODdx7xaSv8w/K2kXyphXeA==";
long size = 92633L;
/*
@sourceURL ��Դ���ص�ַ
@savePath ��Դ���ر���·��
@md5 ��Դ��ϣmd5ֵ
@size ��Դ��С
*/
Resource resource = new Resource(sourceURL, savePath, md5, size, null);
resources.add(resource);
```
��������״̬
```
DownloadProgress downloadProgress = new DownloadProgress() {
	@Override
    protected synchronized void onDownloadChange() {
        //Message.sent(SynchronousHandler.CHANGE_LOADING, new long[]{this.getLoaded(), this.getTotal()});
    }

    @Override
    protected synchronized void onDownloadFailed(Resource resource) {
        //Log.i("c3","failed: "+resource.getPath());
    }

    @Override
    protected synchronized void onDownloadSuccess(Resource resource) {
        //Log.i("c3","success: "+resource.getPath());
    }

    @Override
    protected void onDownloadFinish() { 
    	//���ض���ִ����� �����ɹ���ʧ�ܵ�����
        //Log.i("c3","download all finish.");
    }
};
```
������������
```
int nThreads = 5;
Long chunkSize = 5*1024*1024L;
/*
@nThreads ���߳���������
@chunkSize ��Դ�и���С
*/
BatchDownload batchDownload = BatchDownload.build(resources, downloadProgress, nThreads, chunkSize);
```
OK, ����������˵���  
�����Ҫ��ִ̬�������������
```
batchDownload.download(resource);
```