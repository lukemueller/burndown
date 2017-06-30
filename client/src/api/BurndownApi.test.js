/* global describe, it, expect, fetch, jest */

import 'whatwg-fetch';

import {getProjects} from './BurndownApi';

describe('BurndownApi', () => {

    describe('#getProjects', () => {
        let fetchSpy;
        let getProjectsPromise;
        beforeEach(() => {
            fetchSpy = jest.spyOn(window, 'fetch');
            getProjectsPromise = getProjects();
        });

        it('fires a GET at the `/projects` endpoint', function () {
            expect(fetchSpy).toHaveBeenCalledWith('http://localhost:8080/projects', { method: 'GET'});
        });

        it('returns a promise', () => {
            expect(getProjectsPromise).toBeInstanceOf(Promise);
        });
    });
});
