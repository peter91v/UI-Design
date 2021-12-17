package com.example.easydo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easydo.dao.Task;

import java.util.ArrayList;

/**
 * Adapts the listitem to match our tasklayout
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private final ArrayList<Task> taskList;
    private final Context taskContext;
    private int expandedItem = -1;
    private int oldExpandedItem = -1;

    public RecyclerViewAdapter(Context appContext, ArrayList<Task> tasks) {
        taskList = tasks;
        taskContext = appContext;
    }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     * <p>
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using.
     * Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #getItemViewType(int)
     * @see #onBindViewHolder(ViewHolder, int)
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     * <p>
     * Note that unlike {@link ListView}, RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the <code>position</code> parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use {@link ViewHolder#getAdapterPosition()} which will
     * have the updated adapter position.
     * <p>
     * Override onBindViewHolder(ViewHolder, int, List) instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        //title
        holder.taskTitle.setText(taskList.get(position).getTitle());
        //date
        holder.taskDeadline.setText(taskList.get(position).getDeadline("dd.MM.yyyy"));
        holder.taskCalenderIcon.setVisibility(taskList.get(position).getDeadline("dd.MM.yyyy").isEmpty() ? View.GONE : View.VISIBLE);
        //time
        holder.taskDeadlineTime.setText(taskList.get(position).getDeadline("HH:mm"));
        holder.taskClockIcon.setVisibility(taskList.get(position).getDeadline("HH:mm").isEmpty() ? View.GONE : View.VISIBLE);
        //priority
        switch (taskList.get(position).getPriority()) {
            case 3:
                holder.taskPriority.setBackgroundColor(ContextCompat.getColor(taskContext, R.color.priority_red));
                break;
            case 2:
                holder.taskPriority.setBackgroundColor(ContextCompat.getColor(taskContext, R.color.priority_yellow));
                break;
            case 1:
                holder.taskPriority.setBackgroundColor(ContextCompat.getColor(taskContext, R.color.priority_green));
            default:
                break;
        }
        //checkbox
        if (taskList.get(position).isDone())
            holder.taskCheckbox.setChecked(true);

        holder.taskCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d(TAG, "onCheckboxChanged on item: " + taskList.get(holder.getAdapterPosition()).getTitle() + " Position: " + holder.getAdapterPosition());

                Task completedTask = taskList.get(holder.getAdapterPosition());
                if (isChecked) {
                    completedTask.setDone(true);
                    MainActivity.getTaskManager().deleteTask(holder.getAdapterPosition(), true);
                    MainActivity.getTaskManager().addTask(completedTask, false);

                } else {
                    completedTask.setDone(false);
                    MainActivity.getTaskManager().deleteTask(holder.getAdapterPosition(), false);
                    MainActivity.getTaskManager().addTask(completedTask, true);
                }
                notifyItemRemoved(holder.getAdapterPosition());
            }
        });


        //expand mechanism
        final boolean isExpanded = position == expandedItem;

        holder.taskLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Item " + taskList.get(holder.getAdapterPosition()).getTitle() + " should expand now");

                expandedItem = isExpanded ? -1 : holder.getAdapterPosition();
                notifyItemChanged(oldExpandedItem);
                notifyItemChanged(holder.getAdapterPosition());
            }
        });

        //expand arrow
        if(isExpanded)
            holder.taskArrow.setImageDrawable(MainActivity.getMainContext().getDrawable(android.R.drawable.arrow_up_float));
        else
            holder.taskArrow.setImageDrawable(MainActivity.getMainContext().getDrawable(android.R.drawable.arrow_down_float));
        //location
        holder.taskLocation.setText(taskList.get(position).getLocation());
        holder.taskLocation.setVisibility(isExpanded && !taskList.get(position).getLocation().isEmpty() ? View.VISIBLE : View.GONE);
        holder.taskLocation.setActivated(isExpanded);
        holder.taskLocationIcon.setVisibility(isExpanded && !taskList.get(position).getLocation().isEmpty() ? View.VISIBLE : View.GONE);

        //description
        holder.taskDescription.setText(taskList.get(position).getDescription());
        holder.taskDescription.setVisibility(isExpanded && !taskList.get(position).getDescription().isEmpty() ? View.VISIBLE : View.GONE);
        holder.taskDescription.setActivated(isExpanded);

        //edit Button
        holder.taskEdit.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.taskEdit.setActivated(isExpanded);
        holder.taskEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = ((AppCompatActivity) taskContext).getSupportFragmentManager();

                fragmentManager.beginTransaction().add(R.id.host_fragment_content_main, new AddNewTaskFragment(taskList.get(position))).addToBackStack("edit task").commit();
                deleteTaskListEntry(position);
            }
        });


        //delete Button
        holder.taskDelete.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        holder.taskDelete.setActivated(isExpanded);
        holder.taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alertDialog = new AlertDialog.Builder(v.getContext(), R.style.AppCompatAlertDialogStyle)
                        .setTitle("Do you want to delete this task?")
                        .setCancelable(true)
                        .setPositiveButton(v.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteTaskListEntry(holder.getAdapterPosition());
                                //MainActivity.deleteAlarm(holder.getAdapterPosition());
                            }
                        })
                        .setNegativeButton(v.getResources().getString(R.string.no), new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(v.getResources().getColor(R.color.white));
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(v.getResources().getColor(R.color.white));
            }
        });

        if (isExpanded)
            oldExpandedItem = expandedItem;
    }


    private void deleteTaskListEntry(int position) {
        MainActivity.getTaskManager().deleteTask(position, !taskList.get(position).isDone());
        notifyItemRemoved(position);
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return taskList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout taskLayout;
        CheckBox taskCheckbox;
        TextView taskTitle;
        TextView taskDeadline;
        ImageView taskLocationIcon;
        TextView taskLocation;
        TextView taskDescription;
        ImageButton taskEdit;
        ImageButton taskDelete;
        ImageView taskPriority;
        TextView taskDeadlineTime;
        ImageView taskArrow;
        ImageView taskCalenderIcon;
        ImageView taskClockIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            taskCheckbox = itemView.findViewById(R.id.taskCheckBox);
            taskTitle = itemView.findViewById(R.id.taskTitle);
            taskDeadline = itemView.findViewById(R.id.taskDeadline);
            taskLayout = itemView.findViewById(R.id.taskParent);
            taskLocationIcon = itemView.findViewById(R.id.taskLocation);
            taskLocation = itemView.findViewById(R.id.taskLocationText);
            taskDescription = itemView.findViewById(R.id.taskDescription);
            taskEdit = itemView.findViewById(R.id.taskEdit);
            taskDelete = itemView.findViewById(R.id.taskDelete);
            taskPriority = itemView.findViewById(R.id.taskPriority);
            taskDeadlineTime = itemView.findViewById(R.id.taskDeadlineTime);
            taskArrow = itemView.findViewById(R.id.arrow);
            taskCalenderIcon= itemView.findViewById(R.id.taskCalender);
            taskClockIcon = itemView.findViewById(R.id.taskClock);
        }
    }
}
