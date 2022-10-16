package com.example.myselfchatapp;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.cardview.widget.CardView;
        import androidx.recyclerview.widget.RecyclerView;
        import java.util.ArrayList;

    public class  Chat_RecyclerViewAdapter extends RecyclerView.Adapter<Chat_MyHolder> {

    Context context;
    ArrayList<UserChatMessage> userChatMessage;

    public Chat_RecyclerViewAdapter(Context context, ArrayList<UserChatMessage> userChatMessage){
        this.context = context;
        this.userChatMessage = userChatMessage;
    }
        @Override
        public int getItemViewType(int position) {
            if (userChatMessage.get(position).getSendRecieve() == true){
                return 1;
            }else{
                return 2;
            }
        }
        @Override
        public Chat_MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
           // MyModel.ModelTypes type = MyModel.ModelTypes.values()[viewType];
            //MyModel = userChatMessage
            if (viewType == 1) {
                return Chat_MyHolder.inflateViewByType(true, layoutInflater, parent);
            }else{
                return Chat_MyHolder.inflateViewByType(false, layoutInflater, parent);
            }
        }

    @Override
    public void onBindViewHolder(Chat_MyHolder holder, int position) {

        UserChatMessage model = userChatMessage.get(position);
        if (model.getSendRecieve()) {
            sendViewType((Chat_SendMyViewHolder) holder, model);
        } else {
            recieveViewType((Chat_RecieveMyViewHolder) holder, model);
        }

    }
    private void sendViewType(Chat_SendMyViewHolder holder, UserChatMessage userChatMessage) {
            holder.tvSMessage.setText(userChatMessage.getMessage());
            holder.tvSDate.setText(userChatMessage.getDate());
            holder.tvSTime.setText(userChatMessage.getTime());
            // do what you want with your views in layout_type_1
    }

    private void recieveViewType(Chat_RecieveMyViewHolder holder, UserChatMessage userChatMessage) {
            holder.tvRName.setText("Name : " + userChatMessage.getName());
            switch (userChatMessage.getLogo()) {
                case "arrow":
                    holder.tvRLogo.setImageResource(R.drawable.arrow);
                    break;
                case "circle":
                    holder.tvRLogo.setImageResource(R.drawable.circle);
                    break;
                case "diamond":
                    holder.tvRLogo.setImageResource(R.drawable.diamond);
                    break;
                case "heart":
                    holder.tvRLogo.setImageResource(R.drawable.heart);
                    break;
                case "square":
                    holder.tvRLogo.setImageResource(R.drawable.square);
                    break;
                case "star":
                    holder.tvRLogo.setImageResource(R.drawable.star);
                    break;
                default:
                    holder.tvRLogo.setImageResource(R.drawable.question);
                    break;
            }
            holder.tvRMessage.setText(userChatMessage.getMessage());
            holder.tvRDate.setText(userChatMessage.getDate());
            holder.tvRTime.setText(userChatMessage.getTime());
            // do what you want with your views in layout_type_2
    }

    @Override
    public int getItemCount() {
        //Number of items displayed
        return userChatMessage.size();
    }

}
