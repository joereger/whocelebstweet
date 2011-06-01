package com.celebtwit.cache.providers;

import com.celebtwit.cache.providers.ehcache.EhcacheProvider;
import com.celebtwit.cache.providers.infinispan.InfinispanProvider;
import com.celebtwit.cache.providers.jboss.JbossTreeCacheAOPProvider;
import com.celebtwit.cache.providers.oscache.OsCacheProvider;
import com.celebtwit.cache.providers.oscache.OsCacheClusteredProvider;
import com.celebtwit.cache.providers.dbcache.DbcacheProvider;

/**
 * Factory class to get a cache provider
 */
public class CacheFactory {

    public static CacheProvider getCacheProvider(){
        return getCacheProvider("InfinispanProvider");
    }

    public static CacheProvider getCacheProvider(String providername){
        if (providername.equals("InfinispanProvider")){
            return new InfinispanProvider();
        } else if (providername.equals("EhcacheProvider")){
            return new EhcacheProvider();
        }else if (providername.equals("JbossTreeCacheAOPProvider")){
            return new JbossTreeCacheAOPProvider();
        } else if (providername.equals("OsCacheProvider")){
            return new OsCacheProvider();
        } else if (providername.equals("DbcacheProvider")){
            return new DbcacheProvider();
        } else if (providername.equals("OsCacheClusteredProvider")){
            return new OsCacheClusteredProvider();
        } else {
            return getCacheProvider();
        }
    }

}
