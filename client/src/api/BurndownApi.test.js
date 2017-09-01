/* global describe, it, expect, fetch, jest */
import axios  from 'axios';

import {getProject, getProjects} from './BurndownApi';

describe('BurndownApi', () => {

    describe('#getProjects', () => {
        let fetchSpy;
        let getProjectsPromise;
        beforeEach(() => {
            fetchSpy = jest.spyOn(axios, 'get');
            getProjectsPromise = getProjects();
        });

        it('fires a GET at the `/projects` endpoint', function () {
            expect(fetchSpy).toHaveBeenCalledWith('http://localhost:8080/projects');
        });

        it('returns a promise', () => {
            expect(getProjectsPromise).toBeInstanceOf(Promise);
        });
    });

    describe('#getProject', () => {
        let fetchSpy;
        let getProjectsPromise;
        beforeEach(() => {
            fetchSpy = jest.spyOn(axios, 'get');
            getProjectsPromise = getProject(1);
        });

        it('fires a GET at the `/project/{project-id}` endpoint', function () {
            expect(fetchSpy).toHaveBeenCalledWith('http://localhost:8080/projects/1');
        });

        it('returns a promise', () => {
            expect(getProjectsPromise).toBeInstanceOf(Promise);
        });
    });

    describe('#getProject with parameters', () => {
        let fetchSpy;
        let getProjectsPromise;
        beforeEach(() => {
            fetchSpy = jest.spyOn(axios, 'get');
            getProjectsPromise = getProject(1, 5);
        });

        it('fires a GET at the `/project/{project-id}?numberOfEmployees={numberOfEmployees}` endpoint', function () {
            expect(fetchSpy).toHaveBeenCalledWith('http://localhost:8080/projects/1?numberOfEmployees=5');
        });

        it('returns a promise', () => {
            expect(getProjectsPromise).toBeInstanceOf(Promise);
        });
    });
});
