package jm.through.read

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jm.through.R
import javax.mail.internet.MimeUtility

class ReadAdapter(var dataList: ArrayList<ReadData>): RecyclerView.Adapter<ReadViewHolder>() {
    private var onItemClick: View.OnClickListener? = null //item클릭 시 event

    override fun onBindViewHolder(holder: ReadViewHolder?, position: Int) {
        // ReadDate 요소 가져오기
        var title:String=dataList!!.get(position).mailTitle
        var content:String=dataList!!.get(position).mailContent
        var dates:String =dataList!!.get(position).mailDate
//        var check:Boolean=dataList!!.get(position).check

        // ReadViewHolder의 text를 가져온 ReadData 로 채우기
        holder!!.mailSender.text = title.split("<")[0] // 발신자
        holder!!.mailSubject.text = content // 내용

        if (dates != null) {
            holder!!.mailDate.text = dates//.split(" ")[3];
        } else {
            holder!!.mailDate.text = dates
        }
        Log.v("printing title :" ,title);
        Log.v("printing dates :", dates);

//        if(check)
//        holder!!.checkImg.setBackgroundResource(R.drawable.make_checkbox_on)
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReadViewHolder {
        val mainView:View=LayoutInflater.from(parent!!.context).inflate(R.layout.check_board_item,parent,false)
        mainView.setOnClickListener(onItemClick)
        return ReadViewHolder(mainView)
    }



    override fun getItemCount(): Int = dataList.size

    fun setOnItemClickListener(l: View.OnClickListener) {
        onItemClick = l
    }
}