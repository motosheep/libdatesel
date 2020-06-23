
介绍
日期选择库

使用说明


findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        DateMain.getInstance().show(MainActivity.this, 1);
    }
});
DateMain.getInstance().setOnDateListener(new DateMain.DateSelInfoCallBack() {
    @Override
    public void Date(DateSelResult result) {
        Log.d(TAG, "选择的日期: " + new Gson().toJson(result));
    }
});

