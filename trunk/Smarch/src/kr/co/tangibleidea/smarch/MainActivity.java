package kr.co.tangibleidea.smarch;

import java.util.HashMap;

import kr.co.tangibleidea.R;
import kr.co.tangibleidea.smarch.util.CommonUtil;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.EditText;

public class MainActivity extends FragmentActivity
{
	private int nCurrPageIdx= 0;
    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
     * sections. We use a {@link android.support.v4.app.FragmentPagerAdapter} derivative, which will
     * keep every loaded fragment in memory. If this becomes too memory intensive, it may be best
     * to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Create the adapter that will return a fragment for each of the three primary sections
        // of the app.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        
        SearchData.strISOcode= CommonUtil.getCountrylso(this);
        
        if(SearchData.strISOcode.toUpperCase().equals("KR"))
        {    		
    		SearchData.strURL1Name= "GOOGLE";
    		SearchData.strURL2Name= "NAVER";
    		SearchData.strURL3Name= "DAUM";
        }
        else if(SearchData.strISOcode.toUpperCase().equals("US"))
        {    		
    		SearchData.strURL1Name= "GOOGLE";
    		SearchData.strURL2Name= "BING";
    		SearchData.strURL3Name= "YAHOO";
        }
        else
        {
        	SearchData.strURL1Name= "GOOGLE";
    		SearchData.strURL2Name= "BING";
    		SearchData.strURL3Name= "YAHOO";
        }
        
        mViewPager.setOnPageChangeListener(new OnPageChangeListener()
        {
			
			@Override
			public void onPageSelected(int arg0)
			{
				nCurrPageIdx= arg0;				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {    	
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    } 

    


    @Override
	public void onBackPressed()
    {
    	SectionsPagerAdapter ADT= (SectionsPagerAdapter)mViewPager.getAdapter();
    	DummySectionFragment frag= (DummySectionFragment) ADT.getItem(nCurrPageIdx);
    	if( frag.webView != null )
    	{
    		if(frag.webView.canGoBack())
    		{
    			frag.webView.goBack();
    		}
    		else
    		{
    			
    		}
    	}
    	
		//super.onBackPressed();
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
    {
    	if( item.getItemId() == R.id.search )
    	{
    		AlertDialog.Builder alert = new AlertDialog.Builder(this);
    		
    		alert.setTitle("[Search]");
    		alert.setMessage("Input the search keyword.");
    		
    		// Set an EditText view to get user input
    		final EditText input = new EditText(this);
    		alert.setView(input);
    		
    		alert.setPositiveButton("Search", new DialogInterface.OnClickListener()
    		{
    			public void onClick(DialogInterface dialog, int whichButton)
    			{
    				String value = input.getText().toString();    				
    				SearchData.strQuery= value.toString();    				
    			}
    		});
    		    		
    		alert.setNegativeButton("Cancel",
    				new DialogInterface.OnClickListener() {
    			public void onClick(DialogInterface dialog, int whichButton) {
    				// Canceled.
    			}
    		});
    		
    		alert.show(); 
    	}
    	else if(item.getItemId() == R.id.back )
    	{
    		finish();
    	}
    	else if(item.getItemId() == R.id.menu_settings )
    	{
			Intent intent= new Intent(MainActivity.this, SettingActivity.class);
			startActivity(intent);
			finish();
    	}
    	
		return super.onMenuItemSelected(featureId, item);
	}




	/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter
    {
    	HashMap<Integer, Fragment> map= new HashMap<Integer, Fragment>();
    	
        public SectionsPagerAdapter(FragmentManager fm)
        {
            super(fm);
        }

        @Override
        public Fragment getItem(int i)
        {
        	Fragment fragment= null;
        	
        	fragment= map.get(i);        	
        	if(fragment==null)
        	{
        		fragment = new DummySectionFragment();
        		map.put(i, fragment);       	
        	
	            Bundle args = new Bundle();
	            args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
	            fragment.setArguments(args);           
        	}
            
            return fragment;
        }

        @Override
        public int getCount()
        {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            switch (position)
            {
                case 0: return SearchData.strURL1Name;
                case 1: return SearchData.strURL2Name;
                case 2: return SearchData.strURL3Name;
            }
            return null;
        }
    }


    
    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DummySectionFragment extends Fragment
    {
    	public WebView webView= null;
    	
        public DummySectionFragment()
        {
        }

        public static final String ARG_SECTION_NUMBER = "section_number";

        public void RefreshFragments()
        {
        	
        }
        
        @Override
		public View getView()
		{ 
			return super.getView();
		}

		@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
        	webView= new WebView(getActivity());
        	webView.getSettings().setJavaScriptEnabled(true); 	//자바스크립트 허용
        	
        	Bundle args = getArguments();
        	
        	String URL1="";
        	String URL2="";
        	String URL3="";
        	String URL1Q= "";
        	String URL2Q= "";
        	String URL3Q= "";
        	
        	
        	// ISO-3166-1 에 따른 국가코드별로 검색엔진을 동적으로 변경.
        	if(SearchData.strISOcode.toUpperCase().equals("KR"))
        	{
        		URL1= SearchData.strGoogle;
        		URL2= SearchData.strNaver;
        		URL3= SearchData.strDaum;
        		
        		URL1Q= SearchData.strGoogleQ;
        		URL2Q= SearchData.strNaverQ;
        		URL3Q= SearchData.strDaumQ;
        	}
        	else if(SearchData.strISOcode.toUpperCase().equals("US"))
        	{
        		URL1= SearchData.strGoogle;
        		URL2= SearchData.strBing;
        		URL3= SearchData.strYahoo;
        		
        		URL1Q= SearchData.strGoogleQ;
        		URL2Q= SearchData.strBingQ;
        		URL3Q= SearchData.strYahooQ;
        	}
        	else
        	{
        		URL1= SearchData.strGoogle;
        		URL2= SearchData.strBing;
        		URL3= SearchData.strYahoo;
        		
        		URL1Q= SearchData.strGoogleQ;
        		URL2Q= SearchData.strBingQ;
        		URL3Q= SearchData.strYahooQ;
        	}
        	
        	switch( args.getInt(ARG_SECTION_NUMBER) )
        	{
	        	case 1:
	        	{
	        		if(SearchData.strQuery=="") //검색어가 없으면
	        			webView.loadUrl(URL1);
	        		else
	        			webView.loadUrl(URL1Q + SearchData.strQuery);
	        		break;
	        	}
	        	case 2:
	        	{
	        		if(SearchData.strQuery=="") //검색어가 없으면
	        			webView.loadUrl(URL2);
	        		else
	        			webView.loadUrl(URL2Q + SearchData.strQuery);
	        		break;
	        	}
	        	case 3:
	        	{
	        		if(SearchData.strQuery=="") //검색어가 없으면
	        			webView.loadUrl(URL3);
	        		else
	        			webView.loadUrl(URL3Q + SearchData.strQuery);
	        		break;
	        	}
        	}
        	
        	webView.setWebViewClient(new WebViewClientClass());  
        	
//            TextView textView = new TextView(getActivity());
//            textView.setGravity(Gravity.CENTER);
//            Bundle args = getArguments();
//            textView.setText(Integer.toString(args.getInt(ARG_SECTION_NUMBER)));
            return webView;
        }
    }
    
    

}
