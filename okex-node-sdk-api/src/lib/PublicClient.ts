// tslint:disable:variable-name

import axios, { AxiosInstance } from 'axios';
import * as querystring from 'querystring';

export function PublicClient(
  apiUri = 'https://www.okex.com',
  timeout = 3000,
  axiosConfig = {}
): any {
  const axiosInstance: AxiosInstance = axios.create({
    baseURL: apiUri,
    timeout,
    ...axiosConfig
  });

  async function get(url: string, params?: object): Promise<any> {
    return axiosInstance
      .get(url, { params })
      .then((res: { readonly data: any }) => res.data)
      .catch(error => {
        console.log(
          error.response && error.response !== undefined
            ? JSON.stringify(error.response.data)
            : error
        );
        console.log(error.message ? error.message : `${url} error`);
        throw error;
      });
  }

  return {
    spot(): any {
      return {
        async getSpotInstruments(): Promise<any> {
          return get('/api/spot/v3/instruments');
        },
        async getSpotBook(
          instrument_id: string,
          params?: { readonly size?: string; readonly depth?: string }
        ): Promise<any> {
          return get(`/api/spot/v3/instruments/${instrument_id}/book`, params);
        },
        async getSpotTicker(instrument_id?: string): Promise<any> {
          return instrument_id
            ? get(`/api/spot/v3/instruments/${instrument_id}/ticker`)
            : get('/api/spot/v3/instruments/ticker');
        },
        async getSpotTrade(
          instrument_id: string,
          params?: {
            readonly from?: string;
            readonly to?: string;
            readonly limit?: string;
          }
        ): Promise<any> {
          return get(
            `/api/spot/v3/instruments/${instrument_id}/trades`,
            params
          );
        },
        async getSpotCandles(
          instrument_id: string,
          params?: {
            readonly start?: string;
            readonly end?: string;
            readonly granularity?: number;
          }
        ): Promise<any> {
          return get(
            `/api/spot/v3/instruments/${instrument_id}/candles`,
            params
          );
        }
      };
    },
    futures(): any {
      return {
        async getInstruments(): Promise<any> {
          return get('/api/futures/v3/instruments');
        },
        async getBook(
          instrument_id: string,
          params?: {
            readonly size?: number;
          }
        ): Promise<any> {
          return get(
            `/api/futures/v3/instruments/${instrument_id}/book${
              params ? `?${querystring.stringify(params)}` : ''
            }`
          );
        },
        async getTicker(instrument_id?: string): Promise<any> {
          return get(
            `/api/futures/v3/instruments${
              instrument_id ? `/${instrument_id}` : ''
            }/ticker`
          );
        },
        async getTrades(
          instrument_id: string,
          params?: {
            readonly from?: number;
            readonly to?: number;
            readonly limit?: number;
          }
        ): Promise<any> {
          return get(
            `/api/futures/v3/instruments/${instrument_id}/trades${
              params ? `?${querystring.stringify(params)}` : ''
            }`
          );
        },
        async getCandles(
          instrument_id: string,
          params?: {
            readonly start?: number;
            readonly end?: number;
            readonly granularity?: number;
          }
        ): Promise<any> {
          return get(
            `/api/futures/v3/instruments/${instrument_id}/candles${
              params ? `?${querystring.stringify(params)}` : ''
            }`
          );
        },
        async getIndex(instrument_id: string): Promise<any> {
          return get(`/api/futures/v3/instruments/${instrument_id}/index`);
        },
        async getRate(): Promise<any> {
          return get('/api/futures/v3/rate');
        },
        async getEstimatedPrice(instrument_id: string): Promise<any> {
          return get(
            `/api/futures/v3/instruments/${instrument_id}/estimated_price`
          );
        },
        async getOpenInterest(instrument_id: string): Promise<any> {
          return get(
            `/api/futures/v3/instruments/${instrument_id}/open_interest`
          );
        },
        async getPriceLimit(instrument_id: string): Promise<any> {
          return get(
            `/api/futures/v3/instruments/${instrument_id}/price_limit`
          );
        },
        async getLiquidation(
          instrument_id: string,
          params: {
            readonly status: string;
            readonly from?: number;
            readonly to?: number;
            readonly limit?: number;
          }
        ): Promise<any> {
          return get(
            `/api/futures/v3/instruments/${instrument_id}/liquidation?${querystring.stringify(
              params
            )}`
          );
        },
        async getMarkPrice(instrument_id: string): Promise<any> {
          return get(`/api/futures/v3/instruments/${instrument_id}/mark_price`);
        }
      };
    },
    swap(): any {
      return {
        async getInstruments(): Promise<any> {
          return get('/api/swap/v3/instruments');
        },
        async getDepth(instrument_id: string, size: string): Promise<any> {
          return get(
            `/api/swap/v3/instruments/${instrument_id}/depth${
              size ? `?size=${size}` : ''
            }`
          );
        },
        async getTicker(instrument_id?: string): Promise<any> {
          return get(
            `/api/swap/v3/instruments${
              instrument_id ? `/${instrument_id}` : ''
            }/ticker`
          );
        },
        async getTrades(
          instrument_id: string,
          params?: {
            readonly from?: string;
            readonly to?: string;
            readonly limit?: string;
          }
        ): Promise<any> {
          return get(
            `/api/swap/v3/instruments/${instrument_id}/trades${
              params ? `?${querystring.stringify(params)}` : ''
            }`
          );
        },
        async getCandles(
          instrument_id: string,
          params?: {
            readonly start?: string;
            readonly end?: string;
            readonly granularity?: string;
          }
        ): Promise<any> {
          return get(
            `/api/swap/v3/instruments/${instrument_id}/candles${
              params ? `?${querystring.stringify(params)}` : ''
            }`
          );
        },
        async getIndex(instrument_id: string): Promise<any> {
          return get(`/api/swap/v3/instruments/${instrument_id}/index`);
        },
        async getRate(): Promise<any> {
          return get('/api/swap/v3/rate');
        },
        async getOpenInterest(instrument_id: string): Promise<any> {
          return get(`/api/swap/v3/instruments/${instrument_id}/open_interest`);
        },
        async getPriceLimit(instrument_id: string): Promise<any> {
          return get(`/api/swap/v3/instruments/${instrument_id}/price_limit`);
        },
        async getLiquidation(
          instrument_id: string,
          params: {
            readonly status: string;
            readonly from?: string;
            readonly to?: string;
            readonly limit?: string;
          }
        ): Promise<any> {
          return get(
            `/api/swap/v3/instruments/${instrument_id}/liquidation`,
            params
          );
        },
        async getFundingTime(instrument_id: string): Promise<any> {
          return get(`/api/swap/v3/instruments/${instrument_id}/funding_time`);
        },
        async getMarkPrice(instrument_id: string): Promise<any> {
          return get(`/api/swap/v3/instruments/${instrument_id}/mark_price`);
        },
        async getHistoricalFudingRate(
          instrument_id: string,
          params: {
            readonly from?: string;
            readonly to?: string;
            readonly limit?: string;
          }
        ): Promise<any> {
          return get(
            `/api/swap/v3/instruments/${instrument_id}/historical_funding_rate`,
            params
          );
        }
      };
    },
    ett(): any {
      return {
        getConstituents(ett?: string): Promise<any> {
          return get(`/api/ett/v3/constituents${ett ? `/${ett}` : ''}`);
        },
        getDefinePrice(ett: string): Promise<any> {
          return get(`/api/ett/v3/define-price/${ett}`);
        }
      };
    }
  };
}
