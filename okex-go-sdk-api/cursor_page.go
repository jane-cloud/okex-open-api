package okex

import (
	"github.com/pkg/errors"
	"reflect"
)

/*
 OKEX uses cursor pagination for all REST requests which return arrays
*/
type CursorPage struct {
	// Request page before (newer) this pagination id.
	Before int
	// Request page after (older) this pagination id.
	After int
	// Number of results per request. Maximum 100. (default 100)
	Limit int
}

type PagingResult struct {
	ResultItems  []map[string]string
	CursorBefore string
	CursorAfter  string
}

func LoadPagingResult(r interface{}) (pr *PagingResult, e error) {
	pg := PagingResult{}
	if r == nil {
		return nil, errors.New("Incorrect data format")
	}

	defer func() {
		if r := recover(); r != nil {
			pr = nil
			e = r.(error)
		}
	}()

	t := reflect.TypeOf(r)
	if t.Kind() != reflect.Array && t.Kind() != reflect.Slice {
		return nil, errors.Errorf("Incorrect data format, %+v", r)
	}

	r1 := r.([]interface{})
	r11 := r1[0].([]map[string]string)
	r12 := r1[1].(map[string]string)

	pg.ResultItems = r11
	pg.CursorBefore = r12["OK-BEFORE"]
	pg.CursorAfter = r12["OK-AFTER"]

	if pg.CursorBefore == "" || pg.CursorAfter == "" {
		pg.CursorBefore = r12["BEFORE"]
		pg.CursorAfter = r12["AFTER"]
	}

	return &pg, nil
}
