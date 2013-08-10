package kr.co.tangibleidea.smarch;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Locale;

import kr.co.tangibleidea.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class RankActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link android.support.v4.app.FragmentPagerAdapter} derivative, which
	 * will keep every loaded fragment in memory. If this becomes too memory
	 * intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.rank, menu);
		return true;
	}
	
    @Override
	public void onBackPressed()
    {
    	
		//super.onBackPressed();
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter
	{

		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = new DummySectionFragment();
			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount()
		{
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A dummy fragment representing a section of the app, but that simply
	 * displays dummy text.
	 */
	public static class DummySectionFragment extends Fragment  implements OnClickListener, OnItemClickListener
	{
		ArrayList<String> items = new ArrayList<String>();
		ArrayAdapter<String> adapter;
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static final String ARG_SECTION_NUMBER = "section_number";

		public DummySectionFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.frag_rank, container, false);
						
			Button BTN_refresh= (Button) rootView.findViewById(R.id.btn_refresh);
			BTN_refresh.setOnClickListener(this);
			
			ListView LST_rank= (ListView) rootView.findViewById(R.id.lst_rank);
			LST_rank.setOnItemClickListener(this);
			adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items);
			LST_rank.setAdapter(adapter);
			
			new AsyncSearch().execute(null, null, null);
						
			return rootView;
		}

		@Override
		public void onClick(View v)
		{
			if(v.getId()==R.id.btn_refresh)
			{
				items.clear();
				adapter.notifyDataSetChanged();
				new AsyncSearch().execute(null, null, null);
			}
		
			
		}
		

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
		{
			TextView TXT_caption= (TextView) arg1;		
			
			SearchData.strQuery= TXT_caption.getText().toString();
			Intent intent= new Intent(getActivity(), MainActivity.class);
			startActivity(intent);
		}
		
		private class AsyncSearch extends AsyncTask<String, Void, HttpResponse>
		{

			@Override
			protected HttpResponse doInBackground(String... arg0)
			{
				 items.clear();
			        final String query = "http://openapi.naver.com/search?key=" + "c1b406b32dbbbbeee5f2a36ddc14067f" + "&query=nexearch&target=rank";
			  
			        HttpClient client = new DefaultHttpClient();
			        HttpGet httpGet = new HttpGet(query);
			        ResponseHandler<String> handler = new BasicResponseHandler();
			 
			        try
			        {
			            String response = client.execute(httpGet, handler);
			 
			            XmlPullParser xpp = XmlPullParserFactory.newInstance().newPullParser();
			            xpp.setInput(new StringReader(response));
			 
			            int eventType = xpp.getEventType();
			            boolean isItemBegin = false;
			            while (eventType != XmlPullParser.END_DOCUMENT)
			            {
			                if (eventType == XmlPullParser.START_TAG)
			                {
			                    if (xpp.getName().equals("K"))
			                    {
			                        isItemBegin = true;
			                    }
			                }
			                else if (eventType == XmlPullParser.END_TAG)
			                {
			                    isItemBegin = false;
			                }
			                if (isItemBegin)
			                {
			                    if (eventType == XmlPullParser.TEXT)
			                    {
			                        items.add(xpp.getText());
			                    }
			                }
			                xpp.next();
			                eventType = xpp.getEventType();
			            }
			        } catch (ClientProtocolException e) {
			            e.printStackTrace();
			        } catch (IOException e) {
			            e.printStackTrace();
			        } catch (XmlPullParserException e) {
			            e.printStackTrace();
			        }
			       

					
			        
			        return null;
				}
			
				@Override
				protected void onPostExecute(HttpResponse result)
				{
					adapter.notifyDataSetChanged();
					
					//super.onPostExecute(result);
					
				}
			}

			        
			
		}		
	
}
