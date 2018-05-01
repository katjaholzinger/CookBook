package com.hwr.cookbook.UI;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.hwr.cookbook.Book;
import com.hwr.cookbook.Database;
import com.hwr.cookbook.R;
import com.hwr.cookbook.Recipe;
import com.hwr.cookbook.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * Created by Thomas on 18.03.2018.
 */

public class FragmentBookmarks extends Fragment {
    private LinearLayout linearLayout;
    private User user;

    private SwipeRefreshLayout srl;

    private boolean isFABOpen = false;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<Book> expandableListTitle;
    HashMap<Book, List<Recipe>> expandableListDetail;
    public ArrayList<Book> books;

    public FragmentBookmarks() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_bookmarks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        createFloatActionButton();

        createExpandableList();
    }


    private void createExpandableList() {
        expandableListView = getActivity().findViewById(R.id.list);

        expandableListDetail = ExpandableListDataPump.getData(books);
        if (expandableListDetail != null) {
            expandableListTitle = new ArrayList<Book>(expandableListDetail.keySet());
        } else {
            ArrayList<Book> emptyList = new ArrayList<>();
            expandableListTitle = emptyList;
        }
        expandableListAdapter = new BooksExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
            }
        });

        expandableListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                                          @Override
                                                          public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                                              if (ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
                                                                  int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                                                                  int childPosition = ExpandableListView.getPackedPositionChild(id);

                                                                  final Recipe recipe = expandableListDetail.get(
                                                                          expandableListTitle.get(groupPosition)).get(
                                                                          childPosition);

                                                                  final Book oldBook = expandableListTitle.get(groupPosition);
                                                                  new DialogChangeBook(getActivity(), (ViewGroup) view, oldBook, recipe);

                                                              } else {
                                                                  int groupPosition = ExpandableListView.getPackedPositionGroup(id);
                                                                  final Book book = expandableListTitle.get(groupPosition);
                                                                  Log.d("Fragment Bookmarks", book.name);
                                                                  if (!book.name.equals("Default")) {
                                                                      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                                                                      alertDialogBuilder.setTitle(getActivity().getResources().getString(R.string.DeleteBookName) + ": " + book.name);
                                                                      alertDialogBuilder.setMessage(getActivity().getResources().getString(R.string.DeleteBookMessage));
                                                                      alertDialogBuilder.setPositiveButton(R.string.delete,
                                                                              new DialogInterface.OnClickListener() {
                                                                                  @Override
                                                                                  public void onClick(DialogInterface dialogInterface, int i) {
                                                                                      Database.deleteBook(book);
                                                                                      updateExpandableList(Database.getBookList());
                                                                                  }
                                                                              });
                                                                      alertDialogBuilder.setNegativeButton(R.string.cancel,
                                                                              new DialogInterface.OnClickListener() {
                                                                                  @Override
                                                                                  public void onClick(DialogInterface dialogInterface, int i) {
                                                                                  }
                                                                              });

                                                                      alertDialogBuilder.create().show();
                                                                  }
                                                              }
                                                              return true;
                                                          }
                                                      }
        );

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {


                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                RecipeActivity.recipe = expandableListDetail.get(
                        expandableListTitle.get(groupPosition)).get(
                        childPosition);
                getActivity().startActivity(intent);

                return false;

            }
        });

        srl = getActivity().findViewById(R.id.swipeRefreshBookmarks);
        srl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        setLoad(true);
                        MainActivity ma = (MainActivity) getActivity();
                        ma.loadData();

                        updateExpandableList(Database.getBookList());
                    }
                }
        );
    }


    public void createFloatActionButton() {
        FloatingActionButton fab = getActivity().findViewById(R.id.fab);
        FloatingActionButton fab1 = getActivity().findViewById(R.id.addRecipeFab);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RecipeActivity.class);
                RecipeActivity.recipe = null;
                getActivity().startActivity(intent);
                closeFABMenu();
            }
        });

        FloatingActionButton fab2 = getActivity().findViewById(R.id.addBookFab);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("AddBook");
                View viewInflated = LayoutInflater.from(getContext()).inflate(R.layout.text_input_new_book, (ViewGroup) getView(), false);

                final EditText input = viewInflated.findViewById(R.id.input);
                builder.setView(viewInflated);

                builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Book book = new Book();
                        book.name = input.getText().toString();
                        Database.setNewBook(FirebaseAuth.getInstance().getCurrentUser().getUid(), book);
                        updateExpandableList(Database.getBookList());
                        closeFABMenu();
                    }
                });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        closeFABMenu();
                    }
                });

                builder.show();
            }
        });
        fab.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                if (!isFABOpen) {
                    showFABMenu();
                } else {
                    closeFABMenu();
                }
            }
        });
    }

    private void showFABMenu() {
        isFABOpen = true;
        getActivity().findViewById(R.id.addBookLayout).animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        getActivity().findViewById(R.id.addRecipeLayout).animate().translationY(-getResources().getDimension(R.dimen.standard_105));

        getActivity().findViewById(R.id.addBookText).setVisibility(View.VISIBLE);
        getActivity().findViewById(R.id.addRecipeText).setVisibility(View.VISIBLE);
    }

    private void closeFABMenu() {
        isFABOpen = false;
        getActivity().findViewById(R.id.addBookLayout).animate().translationY(0);
        getActivity().findViewById(R.id.addRecipeLayout).animate().translationY(0);

        getActivity().findViewById(R.id.addBookText).setVisibility(View.GONE);
        getActivity().findViewById(R.id.addRecipeText).setVisibility(View.GONE);
    }

    public void updateExpandableList(ArrayList<Book> books) {
        setLoad(true);

        this.books = books;
        try {

            expandableListView = getActivity().findViewById(R.id.list);
            expandableListDetail = ExpandableListDataPump.getData(books);
            expandableListTitle = new ArrayList<Book>(expandableListDetail.keySet());
            expandableListAdapter = new BooksExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
            expandableListView.setAdapter(expandableListAdapter);
        } catch (Exception e) {
            Log.d("FragmentBookmarks", "Erste aktualisierung schlägt fehl");
        }
        setLoad(false);
    }


    public void setLoad(boolean load) {
        try {
            srl.setRefreshing(load);
        } catch (Exception e) {
            Log.d("FragmentBookmarks", "Can't find SwipeRefreshLayout!");

        }
    }

}
