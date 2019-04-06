package ml.mahbub.projectkrishibid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mahbub on 1/23/18.
 */

public class feedAdapter extends ArrayAdapter<shortfeed> {

    ArrayList<shortfeed> mfeeds;
    int Resource;
    Context context;
    LayoutInflater inflater;

    public feedAdapter(Context context, int resource, ArrayList<shortfeed> objects) {
        super(context, resource, objects);
        mfeeds=objects;
        Resource=resource;
        this.context=context;
        inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){

            convertView=inflater.inflate(Resource,null);
            viewHolder=new ViewHolder();
            viewHolder.d=(TextView)convertView.findViewById(R.id.d);
            viewHolder.node=(TextView)convertView.findViewById(R.id.node);
            viewHolder.nodevalue=(TextView)convertView.findViewById(R.id.nodevalue);



            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();

        }


        viewHolder.d.setText(mfeeds.get(position).getDate());
        viewHolder.node.setText(mfeeds.get(position).getNode());
        viewHolder.nodevalue.setText(mfeeds.get(position).getNodevalue());
        return convertView;
    }




    static class ViewHolder{
        public TextView d;
        public TextView nodevalue;
        public TextView node;

    }
}
